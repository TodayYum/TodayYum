package com.todayyum.board.infra.database;

import com.todayyum.board.infra.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaTagRepository extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findByContent(String content);

    @Query("SELECT bt.tag " +
            "FROM BoardTagEntity bt " +
            "WHERE bt.board.id = :boardId")
    List<TagEntity> findByBoardId(Long boardId);
}
