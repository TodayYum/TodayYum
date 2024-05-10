package com.todayyum.board.infra.database;

import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.board.infra.entity.YummyEntity;
import com.todayyum.member.infra.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaYummyRepository extends JpaRepository<YummyEntity, Long> {
    boolean existsByBoardAndMember(BoardEntity board, MemberEntity member);
    void deleteByBoardAndMember(BoardEntity board, MemberEntity member);
}
