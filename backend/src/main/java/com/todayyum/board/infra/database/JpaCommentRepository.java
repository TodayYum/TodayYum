package com.todayyum.board.infra.database;

import com.todayyum.board.dto.response.CommentListResponse;
import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.board.infra.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT new com.todayyum.board.dto.response.CommentListResponse(" +
            "c.id, " +
            "m.nickname, " +
            "m.id, " +
            "c.content, " +
            "m.profile, " +
            "c.modifiedAt) " +
            "FROM CommentEntity c " +
            "JOIN c.member m " +
            "WHERE c.board = :board")
    List<CommentListResponse> findByBoard(BoardEntity board);

    Optional<CommentEntity> findFirstByBoardOrderByModifiedAtDesc(BoardEntity board);
}
