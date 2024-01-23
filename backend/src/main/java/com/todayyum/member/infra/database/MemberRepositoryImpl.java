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
    public void save(Member member) {
        MemberEntity memberEntity = member.createEntity();
        jpaMemberRepository.save(memberEntity).getId();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaMemberRepository.existsByEmail(email);
    }
}
