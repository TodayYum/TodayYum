package com.todayyum.board.infra.database;

import com.todayyum.board.dto.response.BoardListResponse;
import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.member.infra.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaBoardRepository extends JpaRepository<BoardEntity, Long> {
    @Query("SELECT new com.todayyum.board.dto.response.BoardListResponse(b.id, b.totalScore) " +
            "FROM BoardEntity b " +
            "WHERE b.member = :member")
    List<BoardListResponse> findByMemberId(MemberEntity member);
}
