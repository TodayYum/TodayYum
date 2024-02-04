package com.todayyum.member.application;

import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.request.MemberAddRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddMemberUseCase {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long addMember(MemberAddRequest memberAddRequest) {
        Member member = Member.createMember(memberAddRequest);

        //TODO 예외 처리
        if(memberRepository.existsByEmail(member.getEmail())) {
            throw new IllegalArgumentException();
        }

        member.changePassword(bCryptPasswordEncoder.encode(member.getPassword()));

        return memberRepository.save(member).getId();
    }


}
