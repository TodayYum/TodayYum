package com.todayyum.board.application.repository;

import java.util.List;

public interface BoardTagRepository {
    void save(Long tagId, Long boardId);
    void deleteByBoardIdAndContents(Long boardId, List<String> tags);
    boolean existsByBoardIdAndContent(Long boardId, String content);
}
