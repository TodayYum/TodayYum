package com.todayyum.member;

import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void addMember() {

        //given
        Member member = Member.builder()
                .email("asd1234@naver.com")
                .nickname("yonggkim")
                .password("a123456789")
                .build();

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Assertions.assertEquals(member.getEmail(), savedMember.getEmail());
        Assertions.assertEquals(member.getNickname(), savedMember.getNickname());
    }

    @Test
    @DisplayName("회원검색 테스트")
    void findMember() {

    }
}
