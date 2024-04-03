package com.todayyum.board.application.repository;

import com.todayyum.board.domain.Comment;
import com.todayyum.board.dto.response.CommentListResponse;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    void delete(Comment comment);
    Comment findById(Long commentId);
    List<CommentListResponse> findByBoardId(Long boardId);
}
