package com.todayyum.member.application;

import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.global.util.S3Util;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.request.IntroductionModifyRequest;
import com.todayyum.member.dto.request.NicknameModifyRequest;
import com.todayyum.member.dto.request.PasswordModifyRequest;
import com.todayyum.member.dto.request.ProfileModifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ModifyMemberUseCase {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final S3Util s3Util;

    @Transactional
    public void modifyNickname(NicknameModifyRequest nicknameModifyRequest) {
        Member member = memberRepository.findById(nicknameModifyRequest.getMemberId());

        if(memberRepository.existsByNickname(nicknameModifyRequest.getNickname())) {
            throw new CustomException(ResponseCode.DUPLICATE_NICKNAME);
        }

        member.changeNickname(nicknameModifyRequest.getNickname());
        memberRepository.save(member);
    }

    @Transactional
    public void modifyProfile(ProfileModifyRequest profileModifyRequest) {
        Member member = memberRepository.findById(profileModifyRequest.getMemberId());

        MultipartFile profile = profileModifyRequest.getProfile();

        String profileUrl = s3Util.uploadFile(profile);

        member.changeProfile(profileUrl);
        memberRepository.save(member);
    }

    @Transactional
    public void modifyPassword(PasswordModifyRequest passwordModifyRequest) {
        Member member = memberRepository.findByEmail(passwordModifyRequest.getEmail());

        member.changePassword(bCryptPasswordEncoder.encode(passwordModifyRequest.getPassword()));
        memberRepository.save(member);
    }

    @Transactional
    public void modifyIntroduction(IntroductionModifyRequest introductionModifyRequest) {
        Member member = memberRepository.findById(introductionModifyRequest.getMemberId());

        member.changeIntroduction(introductionModifyRequest.getIntroduction());
        memberRepository.save(member);
    }

}
