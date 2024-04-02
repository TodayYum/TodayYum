package com.todayyum.board.application.repository;

import com.todayyum.board.domain.Comment;

public interface CommentRepository {
    Comment save(Comment comment);
    void delete(Comment comment);
    Comment findById(Long commentId);
}
