package com.todayyum.member.application;

import com.todayyum.member.application.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RemoveMemberUseCase {

    private final MemberRepository memberRepository;

    @Transactional
    public void removeMember(UUID memberId) {
        memberRepository.deleteById(memberId);
    }
}
