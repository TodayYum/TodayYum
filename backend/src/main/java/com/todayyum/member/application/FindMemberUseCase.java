package com.todayyum.member.application;

import com.todayyum.member.application.repository.FollowRepository;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Follow;
import com.todayyum.member.domain.Member;
import com.todayyum.member.domain.ValidationResult;
import com.todayyum.member.dto.response.MemberDetailResponse;
import com.todayyum.member.dto.response.MemberListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FindMemberUseCase {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public MemberDetailResponse findMember(UUID fromMemberId, UUID toMemberId) {
        Member toMember = memberRepository.findById(toMemberId);

        MemberDetailResponse memberDetailResponse = MemberDetailResponse.createResponse(toMember);

        memberDetailResponse.setFollowingCount(followRepository.countByFromMember(toMemberId));
        memberDetailResponse.setFollowerCount(followRepository.countByToMember(toMemberId));

        Follow follow = Follow.createFollow(fromMemberId, toMemberId);

        memberDetailResponse.setFollowing(followRepository.existsByFromMemberAndToMember(follow));
        
        return memberDetailResponse;
    }

    public Page<MemberListResponse> findListByNickname(Pageable pageable, UUID loginMemberId, String nickname) {
        return memberRepository.findByNicknameLike(pageable, loginMemberId, nickname);
    }

    @Transactional
    public Page<MemberListResponse> listFollowing(Pageable pageable, UUID loginMemberId, UUID fromMemberId) {
        return memberRepository.findByFromMember(pageable, loginMemberId, fromMemberId);
    }

    @Transactional
    public Page<MemberListResponse> listFollower(Pageable pageable, UUID loginMemberId, UUID toMemberId) {
        return memberRepository.findByToMember(pageable, loginMemberId, toMemberId);
    }

    public ValidationResult validateEmail(String email) {
        if(!email.matches("^[A-Za-z0-9._]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$")) {
            return ValidationResult.INVALID;
        }

        if(memberRepository.existsByEmail(email)) {
            return ValidationResult.DUPLICATED;
        }

        return ValidationResult.VALID;
    }

    public ValidationResult validateNickname(String nickname) {
        if(nickname.length() > 10) {
            return ValidationResult.INVALID;
        }

        if(memberRepository.existsByNickname(nickname)) {
            return ValidationResult.DUPLICATED;
        }

        return ValidationResult.VALID;
    }

}

