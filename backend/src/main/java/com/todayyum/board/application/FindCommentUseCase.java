package com.todayyum.board.application;

import com.todayyum.board.application.repository.CommentRepository;
import com.todayyum.board.dto.response.CommentListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindCommentUseCase {
    private final CommentRepository commentRepository;

    public List<CommentListResponse> listComment(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }
}
