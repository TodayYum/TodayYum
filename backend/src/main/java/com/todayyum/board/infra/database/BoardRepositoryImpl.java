package com.todayyum.board.infra.database;

import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.domain.Board;
import com.todayyum.board.dto.response.BoardListResponse;
import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.infra.database.JpaMemberRepository;
import com.todayyum.member.infra.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardRepositoryImpl implements BoardRepository {

    private final JpaBoardRepository jpaBoardRepository;
    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Board save(Board board) {
        BoardEntity boardEntity = board.createEntity();

        MemberEntity memberEntity = jpaMemberRepository.findById(board.getMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        boardEntity.changeMember(memberEntity);

        return Board.createBoard(jpaBoardRepository.save(boardEntity));
    }

    @Override
    public List<BoardListResponse> findByMemberId(UUID memberId) {
        MemberEntity memberEntity = jpaMemberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        return jpaBoardRepository.findByMemberId(memberEntity);
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
}
