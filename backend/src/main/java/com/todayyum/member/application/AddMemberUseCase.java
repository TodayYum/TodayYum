package com.todayyum.member.application;

import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.request.MemberAddRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddMemberUseCase {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UUID addMember(MemberAddRequest memberAddRequest) {
        Member member = Member.createMember(memberAddRequest);

        if(memberRepository.existsByEmail(member.getEmail())) {
            throw new CustomException(ResponseCode.DUPLICATE_EMAIL);
        }

        if(memberRepository.existsByNickname(member.getNickname())) {
            throw new CustomException(ResponseCode.DUPLICATE_NICKNAME);
        }

        member.changePassword(bCryptPasswordEncoder.encode(member.getPassword()));

        return memberRepository.save(member).getId();
    }

}
