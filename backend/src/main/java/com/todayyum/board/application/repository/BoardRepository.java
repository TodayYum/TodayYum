package com.todayyum.board.application.repository;

import com.todayyum.board.domain.Board;
import com.todayyum.board.dto.response.BoardListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BoardRepository {
    Board save(Board board);
    Page<BoardListResponse> findByMemberId(Pageable pageable, UUID memberId);
    void deleteById(Long id);
    Board findById(Long id);
    Page<BoardListResponse> findList(Pageable pageable);
    List<BoardListResponse> findTopByYummy();
    List<BoardListResponse> findTopListByYummy();
    Page<BoardListResponse> findListByTag(Pageable pageable, String content);
    Page<BoardListResponse> findByMemberIdAndYummy(Pageable pageable, UUID memberId);
}
