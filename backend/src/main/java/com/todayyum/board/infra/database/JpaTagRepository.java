package com.todayyum.board.infra.database;

import com.todayyum.board.infra.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByContent(String content);
}
