package com.todayyum.board.infra.database;

import com.todayyum.board.infra.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {
}
