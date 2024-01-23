package com.todayyum.member.application;

import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.request.MemberAddRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AddMemberUseCase {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addMember(MemberAddRequest memberAddRequest) {
        Member member = Member.createMember(memberAddRequest);

        //TODO 예외 처리
        if(memberRepository.existsByEmail(member.getEmail())) {
            return;
        }

        member.changePassword(bCryptPasswordEncoder.encode(member.getPassword()));

        memberRepository.save(member);
    }


}
