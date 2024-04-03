package com.todayyum.board.infra.database;

import com.todayyum.board.application.repository.CommentRepository;
import com.todayyum.board.domain.Comment;
import com.todayyum.board.dto.response.CommentListResponse;
import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.board.infra.entity.CommentEntity;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.infra.database.JpaMemberRepository;
import com.todayyum.member.infra.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepositoryImpl implements CommentRepository {
    private final JpaCommentRepository jpaCommentRepository;
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaBoardRepository jpaBoardRepository;

    @Override
    public Comment save(Comment comment) {
        CommentEntity commentEntity = comment.createEntity();

        MemberEntity memberEntity = jpaMemberRepository.findById(comment.getMemberId())
                        .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        BoardEntity boardEntity = jpaBoardRepository.findById(comment.getBoardId())
                        .orElseThrow(() -> new CustomException(ResponseCode.BOARD_ID_NOT_FOUND));

        commentEntity.changeMember(memberEntity);
        commentEntity.changeBoard(boardEntity);

        return Comment.createComment(jpaCommentRepository.save(commentEntity));
    }

    @Override
    public void delete(Comment comment) {
        jpaCommentRepository.deleteById(comment.getId());
    }

    @Override
    public Comment findById(Long commentId) {
        CommentEntity commentEntity = jpaCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ResponseCode.COMMENT_ID_NOT_FOUND));

        return Comment.createComment(commentEntity);
    }

    @Override
    public List<CommentListResponse> findByBoardId(Long boardId) {
        BoardEntity boardEntity = jpaBoardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ResponseCode.COMMENT_ID_NOT_FOUND));

        return jpaCommentRepository.findByBoard(boardEntity);
    }
}
