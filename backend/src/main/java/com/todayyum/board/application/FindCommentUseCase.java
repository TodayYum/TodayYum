package com.todayyum.board.application;

import com.todayyum.board.application.repository.CommentRepository;
import com.todayyum.board.dto.response.CommentListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindCommentUseCase {
    private final CommentRepository commentRepository;

    public Page<CommentListResponse> listComment(Long boardId, Pageable pageable) {
        return commentRepository.findByBoardId(boardId, pageable);
    }
}
