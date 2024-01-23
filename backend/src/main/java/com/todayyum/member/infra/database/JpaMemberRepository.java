package com.todayyum.member.infra.database;

import com.todayyum.member.infra.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByEmail(String email);
}
