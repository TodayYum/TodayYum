package com.todayyum.board.application.repository;

import com.todayyum.board.domain.Comment;
import com.todayyum.board.dto.response.CommentListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository {
    Comment save(Comment comment);
    void delete(Comment comment);
    Comment findById(Long commentId);
    Page<CommentListResponse> findByBoardId(Long boardId, Pageable pageable);
    Comment findLastByBoardId(Long boardId);
}
