package com.todayyum.board.application;

import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.application.repository.YummyRepository;
import com.todayyum.board.domain.Board;
import com.todayyum.board.domain.Yummy;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddYummyUseCase {
    private final YummyRepository yummyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long addYummy(UUID memberId, Long boardId) {
        Yummy yummy = Yummy.createYummy(memberId, boardId);

        if(yummyRepository.existsByMemberIdAndBoardId(yummy)) {
            throw new CustomException(ResponseCode.DUPLICATE_YUMMY);
        }

        Long yummyId = yummyRepository.save(yummy).getId();

        Board board = boardRepository.findById(boardId);

        board.changeYummyCount(board.getYummyCount() + 1);

        boardRepository.save(board);

        return yummyId;
    }
}
