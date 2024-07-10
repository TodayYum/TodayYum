package com.todayyum.board.infra.database;

import com.todayyum.board.infra.entity.BoardTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JpaBoardTagRepository extends JpaRepository<BoardTagEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM BoardTagEntity BT WHERE BT.board.id = :boardId AND BT.tag.content IN :contents")
    void deleteByBoardIdAndContents(Long boardId, List<String> contents);

    @Query("SELECT COUNT(BT) > 0 FROM BoardTagEntity BT WHERE BT.board.id = :boardId AND BT.tag.content = :content")
    boolean existsByBoardIdAndContent(Long boardId, String content);
}
