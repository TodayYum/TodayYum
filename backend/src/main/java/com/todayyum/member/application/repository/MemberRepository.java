package com.todayyum.member.application.repository;

import com.todayyum.member.domain.Member;

import java.util.UUID;

public interface MemberRepository {

    public Member save(Member member);

    public boolean existsByEmail(String email);

    public Member findById(UUID memberId);

    public Member findByEmail(String email);

    public boolean existsByNickname(String nickname);

    public void deleteById(UUID memberId);

}
