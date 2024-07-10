package com.todayyum.board.application.repository;

import com.todayyum.board.domain.Yummy;

public interface YummyRepository {
    Yummy save(Yummy yummy);
    void delete(Yummy yummy);
    boolean existsByMemberIdAndBoardId(Yummy yummy);
}
