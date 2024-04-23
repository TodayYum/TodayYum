package com.todayyum.board.application;

import com.todayyum.board.application.repository.BoardImageRepository;
import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.domain.Board;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.global.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemoveBoardUseCase {

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final S3Util s3Util;

    @Transactional
    public void removeBoard(UUID memberId, Long boardId) {
        if(boardId == null) {
            throw new CustomException(ResponseCode.INTERNAL_SERVER_ERROR);
        }

        Board board = boardRepository.findById(boardId);

        if(!board.getMemberId().equals(memberId)) {
            throw new CustomException(ResponseCode.WRITER_USER_MISMATCH);
        }

        boardImageRepository.findByBoardId(boardId).stream()
                .forEach(boardImage -> s3Util.removeFile(boardImage.getLink()));

        boardRepository.deleteById(boardId);
    }
}
