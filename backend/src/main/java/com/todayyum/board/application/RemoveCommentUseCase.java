package com.todayyum.board.application;

import com.todayyum.board.application.repository.CommentRepository;
import com.todayyum.board.domain.Comment;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemoveCommentUseCase {
    private final CommentRepository commentRepository;

    public void removeComment(Long commentId, UUID memberId) {
        if(memberId == null) {
            throw new CustomException(ResponseCode.INTERNAL_SERVER_ERROR);
        }

        Comment comment = commentRepository.findById(commentId);

        if(comment.getMemberId() != memberId) {
            throw new CustomException(ResponseCode.WRITER_USER_MISMATCH);
        }

        commentRepository.delete(comment);
    }
}
