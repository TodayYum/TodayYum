package com.todayyum.member;

import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Member Repo - 회원 식별자 조회 테스트")
    void findMemberByMemberId() {

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

    @Test
    @DisplayName("Member Repo - 회원 식별자 조회 실패 테스트(멤버 식별자 오류)")
    void findMemberByMemberIdFailByMemberId() {

        //given
        UUID memberId = UUID.randomUUID();

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> memberRepository.findById(memberId));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member Repo - 회원 이메일 조회 테스트")
    void findMemberByEmail() {

        //given
        Member member = Member.builder()
                .email("test@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        UUID memberId = memberRepository.save(member).getId();

        //when
        Member savedMember = memberRepository.findByEmail(member.getEmail());

        //then
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getNickname(), savedMember.getNickname());
        assertEquals(member.getPassword(), savedMember.getPassword());
    }

    @Test
    @DisplayName("Member Repo - 회원 이메일 조회 실패 테스트(이메일 오류)")
    void findMemberByEmailFailByEmail() {

        //given
        String email = "test@test.com";

        //when & then
        assertNull(memberRepository.findByEmail(email));
    }

    @Test
    @DisplayName("Member Repo - 회원 삭제 테스트")
    void deleteMember() {

        //given
        Member member = Member.builder()
                .email("test@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        UUID memberId = memberRepository.save(member).getId();

        //when
        memberRepository.deleteById(memberId);

        //then
        CustomException thrown = assertThrows(CustomException.class,
                () -> memberRepository.findById(memberId));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member Repo - 회원 삭제 실패 테스트(멤버 식별자 오류)")
    void deleteMemberFailByMemberId() {

        //given
        UUID memberId = UUID.randomUUID();

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> memberRepository.deleteById(memberId));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member Repo - 이메일 중복 테스트(중복)")
    void existsByEmailTrue() {

        //given
        String email = "test@test.com";

        Member member = Member.builder()
                .email(email)
                .nickname("test")
                .password("testtest")
                .build();

        UUID memberId = memberRepository.save(member).getId();

        //when
        boolean result = memberRepository.existsByEmail(email);

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Member Repo - 이메일 중복 테스트(중복X)")
    void existsByEmailFalse() {

        //given
        String email = "test@test.com";

        //when
        boolean result = memberRepository.existsByEmail(email);

        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("Member Repo - 닉네임 중복 테스트(중복)")
    void existsByNicknameTrue() {

        //given
        String nickname = "test";

        Member member = Member.builder()
                .email("test@test.com")
                .nickname(nickname)
                .password("testtest")
                .build();

        UUID memberId = memberRepository.save(member).getId();

        //when
        boolean result = memberRepository.existsByNickname(nickname);

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Member Repo - 닉네임 중복 테스트(중복X)")
    void existsByNicknameFalse() {

        //given
        String nickname = "test";

        //when
        boolean result = memberRepository.existsByNickname(nickname);

        //then
        assertFalse(result);
    }
}
