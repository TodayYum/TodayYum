package com.todayyum.board.application.repository;

import com.todayyum.board.domain.Tag;

import java.util.List;

public interface TagRepository {
    Tag findByContent(String content);
    List<Tag> findByBoardId(Long boardId);
    Tag save(String content);
    Tag findTopByBoardId(Long boardId);
}
