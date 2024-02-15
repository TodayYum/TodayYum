package com.todayyum.member.application;

import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.request.NicknameModifyRequest;
import com.todayyum.member.dto.request.PasswordModifyRequest;
import com.todayyum.member.dto.request.ProfileModifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ModifyMemberUseCase {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void modifyNickname(NicknameModifyRequest nicknameModifyRequest) {
        Member member = memberRepository.findById(nicknameModifyRequest.getMemberId());
        log.info(member.toString());

        if(memberRepository.existsByNickname(nicknameModifyRequest.getNickname())) {
            throw new CustomException(ResponseCode.DUPLICATE_NICKNAME);
        }

        member.changeNickname(nicknameModifyRequest.getNickname());
        memberRepository.save(member);
    }

    public void modifyProfile(ProfileModifyRequest profileModifyRequest) {
    }

    public void modifyPassword(PasswordModifyRequest passwordModifyRequest) {
        Member member = memberRepository.findById(passwordModifyRequest.getMemberId());

        //TODO 비밀번호 형식 확인

        member.changePassword(bCryptPasswordEncoder.encode(passwordModifyRequest.getPassword()));
        memberRepository.save(member);
    }

}
