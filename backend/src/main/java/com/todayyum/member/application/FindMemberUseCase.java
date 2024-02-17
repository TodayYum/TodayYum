package com.todayyum.member.application;

import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.domain.ValidationResult;
import com.todayyum.member.dto.response.MemberDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FindMemberUseCase {

    private final MemberRepository memberRepository;

    public MemberDetailResponse findMember(UUID memberId) {
        Member member = memberRepository.findById(memberId);
        return MemberDetailResponse.createResponse(member);
    }

    public ValidationResult checkEmailDuplication(String email) {
        if(!email.matches("^[A-Za-z0-9._]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$")) {
            return ValidationResult.INVALID;
        }

        if(memberRepository.existsByEmail(email)) {
            return ValidationResult.DUPLICATED;
        }

        return ValidationResult.VALID;
    }

    public ValidationResult checkNicknameDuplication(String nickname) {
        if(nickname.length() > 10) {
            return ValidationResult.INVALID;
        }

        if(memberRepository.existsByNickname(nickname)) {
            return ValidationResult.DUPLICATED;
        }

        return ValidationResult.VALID;
    }

}

