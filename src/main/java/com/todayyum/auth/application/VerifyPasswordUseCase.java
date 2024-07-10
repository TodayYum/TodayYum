package com.todayyum.auth.application;

import com.todayyum.auth.dto.request.PasswordVerifyRequest;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerifyPasswordUseCase {
    private final MemberRepository memberRepository;
    public boolean verifyPassword(PasswordVerifyRequest passwordVerifyRequest) {
        Member member = memberRepository.findById(passwordVerifyRequest.getMemberId());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return bCryptPasswordEncoder.matches(passwordVerifyRequest.getPassword(), member.getPassword());
    }
}
