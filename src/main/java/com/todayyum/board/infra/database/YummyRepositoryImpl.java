package com.todayyum.board.infra.database;

import com.todayyum.board.application.repository.YummyRepository;
import com.todayyum.board.domain.Yummy;
import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.board.infra.entity.YummyEntity;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.infra.database.JpaMemberRepository;
import com.todayyum.member.infra.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class YummyRepositoryImpl implements YummyRepository {
    private final JpaYummyRepository jpaYummyRepository;
    private final JpaBoardRepository jpaBoardRepository;
    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Yummy save(Yummy yummy) {
        MemberEntity memberEntity = jpaMemberRepository.findById(yummy.getMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        BoardEntity boardEntity = jpaBoardRepository.findById(yummy.getBoardId())
                .orElseThrow(() -> new CustomException(ResponseCode.BOARD_ID_NOT_FOUND));

        YummyEntity yummyEntity = YummyEntity.builder()
                .member(memberEntity)
                .board(boardEntity)
                .build();

        return Yummy.createYummy(jpaYummyRepository.save(yummyEntity));
    }

    @Override
    public void delete(Yummy yummy) {
        BoardEntity boardEntity = jpaBoardRepository.findById(yummy.getBoardId())
                .orElseThrow(() -> new CustomException(ResponseCode.BOARD_ID_NOT_FOUND));

        MemberEntity memberEntity = jpaMemberRepository.findById(yummy.getMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        jpaYummyRepository.deleteByBoardAndMember(boardEntity, memberEntity);
    }

    @Override
    public boolean existsByMemberIdAndBoardId(Yummy yummy) {
        MemberEntity memberEntity = jpaMemberRepository.findById(yummy.getMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        BoardEntity boardEntity = jpaBoardRepository.findById(yummy.getBoardId())
                .orElseThrow(() -> new CustomException(ResponseCode.BOARD_ID_NOT_FOUND));

        return jpaYummyRepository.existsByBoardAndMember(boardEntity, memberEntity);
    }
}
