package com.todayyum.board.application;

import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.dto.request.BoardModifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModifyBoardUseCase {
    private final BoardRepository boardRepository;

    @Transactional
    public Long modifyBoard(BoardModifyRequest boardModifyRequest) {

        return null;
    }
}
