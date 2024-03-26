package com.todayyum.member.application.repository;

import com.todayyum.member.domain.Member;

import java.util.UUID;

public interface MemberRepository {

    Member save(Member member);

    boolean existsByEmail(String email);

    Member findById(UUID memberId);

    Member findByEmail(String email);

    boolean existsByNickname(String nickname);

    void deleteById(UUID memberId);
}
