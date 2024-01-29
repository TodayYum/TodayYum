package com.todayyum.member.application;

import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.response.MemberDetailResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FindMemberUseCase {

    private final MemberRepository memberRepository;

    public MemberDetailResponse findMember(Long memberId) {
        Member member = memberRepository.findById(memberId);
        return MemberDetailResponse.createResponse(member);
    }
}

