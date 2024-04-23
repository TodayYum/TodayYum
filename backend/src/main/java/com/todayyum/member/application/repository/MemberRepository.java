package com.todayyum.member.application.repository;

import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.response.MemberListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MemberRepository {

    Member save(Member member);

    boolean existsByEmail(String email);

    Member findById(UUID memberId);

    Member findByEmail(String email);

    boolean existsByNickname(String nickname);

    void deleteById(UUID memberId);
    Page<MemberListResponse> findByNicknameLike(Pageable pageable, UUID loginMemberId, String nickname);
    Page<MemberListResponse> findByFromMember(Pageable pageable, UUID loginMemberId, UUID fromMemberId);

    Page<MemberListResponse> findByToMember(Pageable pageable, UUID loginMemberId, UUID toMemberId);
}
