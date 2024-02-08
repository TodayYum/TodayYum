package com.todayyum.member.infra.database;

import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.infra.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryImpl implements MemberRepository {
    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = member.createEntity();
        return Member.createMember(jpaMemberRepository.save(memberEntity));
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaMemberRepository.existsByEmail(email);
    }

    @Override
    public Member findById(Long memberId) {
        MemberEntity memberEntity = jpaMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException());

        return Member.createMember(memberEntity);
    }

    @Override
    public Member findByEmail(String email) {
        return jpaMemberRepository.findByEmail(email).map(Member::createMember).orElse(null);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return jpaMemberRepository.existsByNickname(nickname);
    }
}
