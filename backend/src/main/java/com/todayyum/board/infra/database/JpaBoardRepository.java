package com.todayyum.board.infra.database;

import com.todayyum.board.dto.response.BoardListResponse;
import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.member.infra.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface JpaBoardRepository extends JpaRepository<BoardEntity, Long> {
    @Query("SELECT new com.todayyum.board.dto.response.BoardListResponse(b.id, b.totalScore, b.yummyCount, b.category) " +
            "FROM BoardEntity b " +
            "WHERE b.member = :member")
    Page<BoardListResponse> findByMember(Pageable pageable, MemberEntity member);

    @Query("SELECT new com.todayyum.board.dto.response.BoardListResponse(b.id, b.totalScore, b.yummyCount, b.category) " +
            "FROM BoardEntity b")
    Page<BoardListResponse> findList(Pageable pageable);

    @Query("SELECT new com.todayyum.board.dto.response.BoardListResponse(b.id, b.totalScore, b.yummyCount, b.category) " +
            "FROM BoardEntity b " +
            "WHERE b.ateAt = :today " +
            "ORDER BY b.yummyCount DESC " +
            "LIMIT 1")
    List<BoardListResponse> findTopByYummyCount(LocalDate today);

    @Query("SELECT new com.todayyum.board.dto.response.BoardListResponse(b.id, b.totalScore, b.yummyCount, b.category) " +
            "FROM BoardEntity b " +
            "WHERE b.id IN (" +
            "SELECT b2.id FROM BoardEntity b2 " +
            "WHERE b2.ateAt = :today AND b2.yummyCount = (" +
            "SELECT MAX(b3.yummyCount) FROM BoardEntity b3 WHERE b3.category = b2.category AND b3.ateAt = :today)) " +
            "ORDER BY b.yummyCount DESC")
    List<BoardListResponse> findTopListByYummyCount(LocalDate today);

    @Query("SELECT new com.todayyum.board.dto.response.BoardListResponse(b.id, b.totalScore, b.yummyCount, b.category) " +
            "FROM BoardEntity b JOIN b.boardTags bt JOIN bt.tag t " +
            "WHERE t.content = :content")
    Page<BoardListResponse> findListByTag(Pageable pageable, String content);

    @Query("SELECT new com.todayyum.board.dto.response.BoardListResponse(b.id, b.totalScore, b.yummyCount, b.category) " +
            "FROM YummyEntity y JOIN y.board b " +
            "WHERE y.member = :member")
    Page<BoardListResponse> findListByMemberAndYummy(Pageable pageable, MemberEntity member);
}
