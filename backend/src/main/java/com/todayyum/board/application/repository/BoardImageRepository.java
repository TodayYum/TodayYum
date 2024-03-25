package com.todayyum.board.application.repository;

import com.todayyum.board.domain.BoardImage;

import java.util.List;

public interface BoardImageRepository {
    void saveAll(List<BoardImage> boardImages);
}
