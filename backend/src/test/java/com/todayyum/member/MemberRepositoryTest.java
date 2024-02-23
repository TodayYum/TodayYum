package com.todayyum.member;

import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
                .email("qwerasdf1234@naver.com")
                .nickname("bonnnnnkim")
                .password("a123456789")
                .build();

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Assertions.assertEquals(member.getEmail(), savedMember.getEmail());
        Assertions.assertEquals(member.getNickname(), savedMember.getNickname());
        Assertions.assertEquals(member.getPassword(), savedMember.getPassword());
    }

    @Test
    @DisplayName("Member Repo - 회원 조회 테스트")
    void findMember() {

        //given
        Member member = Member.builder()
                .email("qwerasdf1234@naver.com")
                .nickname("bonnnnnkim")
                .password("a123456789")
                .build();

        UUID memberId = memberRepository.save(member).getId();

        //when
        Member savedMember = memberRepository.findById(memberId);

        //then
        Assertions.assertEquals(member.getEmail(), savedMember.getEmail());
        Assertions.assertEquals(member.getNickname(), savedMember.getNickname());
        Assertions.assertEquals(member.getPassword(), savedMember.getPassword());
    }
}
