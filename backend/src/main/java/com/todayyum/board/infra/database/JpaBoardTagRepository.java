package com.todayyum.board.infra.database;

import com.todayyum.board.infra.entity.BoardTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBoardTagRepository extends JpaRepository<BoardTagEntity, Long> {
}
