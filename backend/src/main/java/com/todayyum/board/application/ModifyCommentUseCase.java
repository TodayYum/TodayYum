package com.todayyum.board.application;

import com.todayyum.board.application.repository.CommentRepository;
import com.todayyum.board.domain.Comment;
import com.todayyum.board.dto.request.CommentModifyRequest;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModifyCommentUseCase {
    private final CommentRepository commentRepository;

    @Transactional
    public Long modifyComment(CommentModifyRequest commentModifyRequest) {
        Comment comment = commentRepository.findById(commentModifyRequest.getId());

        if(comment.getMemberId() != commentModifyRequest.getMemberId()) {
            throw new CustomException(ResponseCode.WRITER_USER_MISMATCH);
        }

        comment.changeContent(commentModifyRequest.getContent());

        return commentRepository.save(comment).getId();
    }
}
