package com.todayyum.board.application.repository;

import com.todayyum.board.domain.Board;
import com.todayyum.board.dto.response.BoardListResponse;

import java.util.List;
import java.util.UUID;

public interface BoardRepository {
    Board save(Board board);
    List<BoardListResponse> findByMemberId(UUID memberId);
    void deleteById(Long id);
    Board findById(Long id);
}
