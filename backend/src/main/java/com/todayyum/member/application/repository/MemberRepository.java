package com.todayyum.member.application.repository;

import com.todayyum.member.domain.Member;

public interface MemberRepository {

    public Member save(Member member);

    public boolean existsByEmail(String email);

    public Member findById(Long memberId);

    public Member findByEmail(String email);
}
