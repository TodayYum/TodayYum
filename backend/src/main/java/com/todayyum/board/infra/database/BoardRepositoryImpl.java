package com.todayyum.board.infra.database;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.domain.Board;
import com.todayyum.board.dto.request.BoardSearchRequest;
import com.todayyum.board.dto.response.BoardListResponse;
import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.board.infra.entity.QBoardEntity;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.infra.database.JpaMemberRepository;
import com.todayyum.member.infra.entity.MemberEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardRepositoryImpl implements BoardRepository {

    private final JpaBoardRepository jpaBoardRepository;
    private final JpaMemberRepository jpaMemberRepository;
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public Board save(Board board) {
        BoardEntity boardEntity = board.createEntity();

        MemberEntity memberEntity = jpaMemberRepository.findById(board.getMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        boardEntity.changeMember(memberEntity);

        return Board.createBoard(jpaBoardRepository.save(boardEntity));
    }

    @Override
    public Page<BoardListResponse> findByMemberId(Pageable pageable, UUID memberId) {
        MemberEntity memberEntity = jpaMemberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        return jpaBoardRepository.findByMember(pageable, memberEntity);
    }

    @Override
    public void deleteById(Long id) {
        BoardEntity boardEntity = jpaBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ResponseCode.BOARD_ID_NOT_FOUND));

        jpaBoardRepository.delete(boardEntity);
    }

    @Override
    public Board findById(Long id) {
        BoardEntity boardEntity = jpaBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ResponseCode.BOARD_ID_NOT_FOUND));

        return Board.createBoard(boardEntity);
    }

    @Override
    public Page<BoardListResponse> findList(Pageable pageable, BoardSearchRequest boardSearchRequest) {
        QBoardEntity board = QBoardEntity.boardEntity;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (boardSearchRequest.getCategories() != null && !boardSearchRequest.getCategories().isEmpty()) {
            booleanBuilder.and(board.category.in(boardSearchRequest.getCategories()));
        }

        List<BoardListResponse> boardListResponses = queryFactory
                .select(Projections.constructor(BoardListResponse.class,
                        board.id,
                        board.totalScore,
                        board.yummyCount,
                        board.category))
                .from(board)
                .where(booleanBuilder)
                .orderBy(getOrderSpecifier(boardSearchRequest.getSortBy(), board))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(boardListResponses, pageable, boardListResponses.size());
    }

    private OrderSpecifier<?>[] getOrderSpecifier(String sortBy, QBoardEntity board) {
        switch (sortBy) {
            case "rating":
                return new OrderSpecifier[]{board.totalScore.desc(), board.createdAt.desc()};
            case "yummy":
                return new OrderSpecifier[]{board.yummyCount.desc(), board.createdAt.desc()};
            default:
                return new OrderSpecifier[]{board.createdAt.desc()};
        }
    }

    @Override
    public List<BoardListResponse> findTopByYummy() {
        return jpaBoardRepository.findTopByYummyCount(LocalDate.now(ZoneId.of("Asia/Seoul")));
    }

    @Override
    public List<BoardListResponse> findTopListByYummy() {
        Query nativeQuery = em.createNativeQuery("SELECT a.id, a.yummy_count, a.total_score, NULL, NULL, a.category " +
                        "FROM (SELECT " +
                        "b.id, b.yummy_count, b.total_score, b.category, " +
                        "ROW_NUMBER() OVER (" +
                        "PARTITION BY b.category " +
                        "ORDER BY b.yummy_count DESC, b.id ASC" +
                        ") AS 'rank'" +
                        "FROM " +
                        "boards b " +
                        "WHERE " +
                        "b.ate_at = :today) as a " +
                        "WHERE a.rank = 1")
                .setParameter("today", LocalDate.now(ZoneId.of("Asia/Seoul")));

        JpaResultMapper jpaResultMapper = new JpaResultMapper();

        return jpaResultMapper.list(nativeQuery, BoardListResponse.class);
    }

    @Override
    public Page<BoardListResponse> findListByTag(Pageable pageable, String content) {
        return jpaBoardRepository.findListByTag(pageable, content);
    }

    @Override
    public Page<BoardListResponse> findByMemberIdAndYummy(Pageable pageable, UUID memberId) {
        MemberEntity memberEntity = jpaMemberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        return jpaBoardRepository.findListByMemberAndYummy(pageable, memberEntity);
    }
}
