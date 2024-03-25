package com.todayyum.board.infra.database;

import com.todayyum.board.infra.entity.BoardImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBoardImageRepository extends JpaRepository<BoardImageEntity, Long> {
}
