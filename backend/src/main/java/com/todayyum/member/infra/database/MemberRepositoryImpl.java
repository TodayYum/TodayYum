package com.todayyum.member.infra.database;

import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.response.MemberListResponse;
import com.todayyum.member.infra.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryImpl implements MemberRepository {
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaFollowRepository jpaFollowRepository;

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = member.createEntity();
        return Member.createMember(jpaMemberRepository.save(memberEntity));
    }

    @Override
    public Member findById(UUID memberId) {
        MemberEntity memberEntity = findMemberById(memberId);

        return Member.createMember(memberEntity);
    }

    @Override
    public Member findByEmail(String email) {
        return jpaMemberRepository.findByEmail(email).map(Member::createMember).orElse(null);
    }

    @Override
    public Page<MemberListResponse> findByFromMember(Pageable pageable, UUID loginMemberId, UUID fromMemberId) {
        MemberEntity fromMember = findMemberById(fromMemberId);

        Page<MemberListResponse> memberListResponses = jpaMemberRepository.findByFromMember(pageable, fromMember);

        existsByFollowings(loginMemberId, memberListResponses);

        return memberListResponses;
    }

    @Override
    public Page<MemberListResponse> findByToMember(Pageable pageable, UUID loginMemberId, UUID toMemberId) {
        MemberEntity toMember = findMemberById(toMemberId);

        Page<MemberListResponse> memberListResponses = jpaMemberRepository.findByToMember(pageable, toMember);

        existsByFollowings(loginMemberId, memberListResponses);

        return memberListResponses;
    }

    @Override
    public void deleteById(UUID memberId) {
        MemberEntity memberEntity = jpaMemberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        jpaMemberRepository.delete(memberEntity);
    }

    @Override
    public Page<MemberListResponse> findByNicknameLike(Pageable pageable, UUID loginMemberId, String nickname) {
        Page<MemberListResponse> memberListResponses = jpaMemberRepository.findByNicknameLike(pageable, nickname);

        existsByFollowings(loginMemberId, memberListResponses);

        return memberListResponses;
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaMemberRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return jpaMemberRepository.existsByNickname(nickname);
    }

    private void existsByFollowings(UUID loginMemberId, Page<MemberListResponse> memberListResponses) {
        List<UUID> ids = memberListResponses.stream().map(memberListResponse -> memberListResponse.getMemberId())
                .collect(Collectors.toList());

        Set<UUID> followingIds = jpaFollowRepository.findByFromMemberAndToMembers(loginMemberId, ids).stream()
                .map(followEntity -> followEntity.getToMember().getId())
                .collect(Collectors.toSet());

        memberListResponses.forEach(memberListResponse -> {
            memberListResponse.setFollowing(followingIds.contains(memberListResponse.getMemberId()));
        });
    }

    private MemberEntity findMemberById(UUID memberId) {
        return jpaMemberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));
    }
}
