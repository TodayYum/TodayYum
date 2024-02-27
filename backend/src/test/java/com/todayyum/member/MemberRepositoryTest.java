package com.todayyum.member;

import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Member Repo - 회원 가입 테스트")
    void addMember() {

        //given
        Member member = Member.builder()
                .email("test@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getNickname(), savedMember.getNickname());
        assertEquals(member.getPassword(), savedMember.getPassword());
    }

    @Test
    @DisplayName("Member Repo - 회원 조회 테스트")
    void findMember() {

        //given
        Member member = Member.builder()
                .email("test@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        UUID memberId = memberRepository.save(member).getId();

        //when
        Member savedMember = memberRepository.findById(memberId);

        //then
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getNickname(), savedMember.getNickname());
        assertEquals(member.getPassword(), savedMember.getPassword());
    }
}
