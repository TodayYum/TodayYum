package com.todayyum.board.infra.database;

import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.board.infra.entity.BoardImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaBoardImageRepository extends JpaRepository<BoardImageEntity, Long> {
    List<BoardImageEntity> findByBoard(BoardEntity boardEntity);

    @Query("SELECT bi FROM BoardImageEntity bi JOIN bi.board b JOIN b.member m WHERE m.id = :memberId")
    List<BoardImageEntity> findByMemberId(UUID memberId);

    @Query(value = "SELECT * FROM board_images WHERE board_id = :boardId LIMIT 1", nativeQuery = true)
    Optional<BoardImageEntity> findThumbnailByBoardId(Long boardId);
}
