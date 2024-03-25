package com.todayyum.board.application;

import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.dto.response.BoardListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindBoardUseCase {

    private final BoardRepository boardRepository;

    public List<BoardListResponse> listBoardByMember(UUID memberId) {
        return boardRepository.findByMemberId(memberId);
    }
}
