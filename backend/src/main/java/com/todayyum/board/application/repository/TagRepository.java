package com.todayyum.board.application.repository;

import com.todayyum.board.domain.Tag;

public interface TagRepository {
    Tag findByContent(String content);

    Tag save(String content);
}
