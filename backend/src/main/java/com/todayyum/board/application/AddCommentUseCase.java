package com.todayyum.board.application;

import com.todayyum.board.application.repository.CommentRepository;
import com.todayyum.board.domain.Comment;
import com.todayyum.board.dto.request.CommentAddRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddCommentUseCase {
    private final CommentRepository commentRepository;

    @Transactional
    public Long addComment(CommentAddRequest commentAddRequest) {
        Comment comment = Comment.createComment(commentAddRequest);

        return commentRepository.save(comment).getId();
    }
}
