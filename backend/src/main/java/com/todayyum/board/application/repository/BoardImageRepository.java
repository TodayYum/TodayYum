package com.todayyum.board.application.repository;

import com.todayyum.board.domain.BoardImage;

import java.util.List;
import java.util.UUID;

public interface BoardImageRepository {
    void saveAll(List<BoardImage> boardImages);

    List<BoardImage> findByBoardId(Long boardId);

    List<BoardImage> findByMemberId(UUID memberId);

    BoardImage findThumbnailByBoardId(Long boardId);
}
