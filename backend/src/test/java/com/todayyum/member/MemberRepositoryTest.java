package com.todayyum.member;

import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.repository.FollowRepository;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Follow;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.response.MemberListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FollowRepository followRepository;

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

        memberRepository.save(member).getId();

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

    @Test
    @DisplayName("Member Repo - 팔로우 테스트")
    void addFollow() {
        //given
        Member fromMember = Member.builder()
                .email("test1234@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        Member toMember = Member.builder()
                .email("test12345@test.com")
                .nickname("test2")
                .password("testtest")
                .build();

        Member savedFromMember = memberRepository.save(fromMember);

        Member savedToMember = memberRepository.save(toMember);

        Follow follow = Follow.createFollow(savedFromMember.getId(), savedToMember.getId());

        //when
        Follow savedFollow = followRepository.save(follow);

        //then
        assertEquals(follow.getFromMemberId(), savedFollow.getFromMemberId());
        assertEquals(follow.getToMemberId(), savedFollow.getToMemberId());
    }

    @Test
    @DisplayName("Member Repo - 팔로우 실패 테스트(멤버 식별자 오류)")
    void addFollowFailByMemberId() {
        //given
        Follow follow = Follow.createFollow(UUID.randomUUID(), UUID.randomUUID());

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> followRepository.save(follow));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member Repo - 언팔로우 테스트")
    void removeFollow() {
        //given
        Member fromMember = Member.builder()
                .email("test1234@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        Member toMember = Member.builder()
                .email("test12345@test.com")
                .nickname("test2")
                .password("testtest")
                .build();

        Member savedFromMember = memberRepository.save(fromMember);

        Member savedToMember = memberRepository.save(toMember);

        Follow follow = Follow.createFollow(savedFromMember.getId(), savedToMember.getId());

        followRepository.save(follow);

        //when
        followRepository.delete(follow);

        //then
        assertEquals(false, followRepository.existsByFromMemberAndToMember(follow));
    }

    @Test
    @DisplayName("Member Repo - 언팔로우 실패 테스트(멤버 식별자 오류)")
    void removeFollowFailByMemberId() {
        //given
        Follow follow = Follow.createFollow(UUID.randomUUID(), UUID.randomUUID());

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> followRepository.delete(follow));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member Repo - 팔로잉 리스트 테스트")
    void listFollowing() {
        //given
        Member fromMember = Member.builder()
                .email("test1234@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        Member toMember = Member.builder()
                .email("test12345@test.com")
                .nickname("test2")
                .password("testtest")
                .build();

        Member savedFromMember = memberRepository.save(fromMember);

        Member savedToMember = memberRepository.save(toMember);

        Follow follow = Follow.createFollow(savedFromMember.getId(), savedToMember.getId());

        followRepository.save(follow);

        Pageable pageable = Pageable.ofSize(10);

        //when
        Page<MemberListResponse> memberListResponses = memberRepository.findByFromMember(
                pageable, savedFromMember.getId(), savedFromMember.getId());

        //then
        assertEquals(savedToMember.getId(), memberListResponses.getContent().get(0).getMemberId());
        assertEquals(savedToMember.getNickname(), memberListResponses.getContent().get(0).getNickname());
    }

    @Test
    @DisplayName("Member Repo - 팔로잉 리스트 실패 테스트(멤버 식별자 오류)")
    void listFollowingFailByMemberId() {
        //given
        UUID memberId = UUID.randomUUID();

        Pageable pageable = Pageable.ofSize(10);

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> memberRepository.findByFromMember(pageable, memberId, memberId));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member Repo - 팔로워 리스트 테스트")
    void listFollower() {
        //given
        Member fromMember = Member.builder()
                .email("test1234@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        Member toMember = Member.builder()
                .email("test12345@test.com")
                .nickname("test2")
                .password("testtest")
                .build();

        Member savedFromMember = memberRepository.save(fromMember);

        Member savedToMember = memberRepository.save(toMember);

        Follow follow = Follow.createFollow(savedFromMember.getId(), savedToMember.getId());

        followRepository.save(follow);

        Pageable pageable = Pageable.ofSize(10);

        //when
        Page<MemberListResponse> memberListResponses = memberRepository.findByToMember(
                pageable, savedToMember.getId(), savedToMember.getId());

        //then
        assertEquals(savedFromMember.getId(), memberListResponses.getContent().get(0).getMemberId());
        assertEquals(savedFromMember.getNickname(), memberListResponses.getContent().get(0).getNickname());
    }

    @Test
    @DisplayName("Member Repo - 팔로워 리스트 실패 테스트(멤버 식별자 오류)")
    void listFollowerFailByMemberId() {
        //given
        UUID memberId = UUID.randomUUID();
        Pageable pageable = Pageable.ofSize(10);


        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> memberRepository.findByToMember(pageable, memberId, memberId));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member Repo - 팔로우 중복 테스트(중복 X)")
    void existsByFromMemberAndToMemberFalse() {
        //given
        Member fromMember = Member.builder()
                .email("test1234@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        Member toMember = Member.builder()
                .email("test12345@test.com")
                .nickname("test2")
                .password("testtest")
                .build();

        Member savedFromMember = memberRepository.save(fromMember);

        Member savedToMember = memberRepository.save(toMember);

        Follow follow = Follow.createFollow(savedFromMember.getId(), savedToMember.getId());

        //when & then
        assertFalse(followRepository.existsByFromMemberAndToMember(follow));
    }

    @Test
    @DisplayName("Member Repo - 팔로우 중복 테스트(중복)")
    void existsByFromMemberAndToMemberTrue() {
        //given
        Member fromMember = Member.builder()
                .email("test1234@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        Member toMember = Member.builder()
                .email("test12345@test.com")
                .nickname("test2")
                .password("testtest")
                .build();

        Member savedFromMember = memberRepository.save(fromMember);

        Member savedToMember = memberRepository.save(toMember);

        Follow follow = Follow.createFollow(savedFromMember.getId(), savedToMember.getId());

        followRepository.save(follow);

        //when & then
        assertTrue(followRepository.existsByFromMemberAndToMember(follow));
    }

    @Test
    @DisplayName("Member Repo - 팔로잉 수 테스트")
    void countByFromMember() {
        //given
        Member fromMember = Member.builder()
                .email("test1234@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        Member toMember = Member.builder()
                .email("test12345@test.com")
                .nickname("test2")
                .password("testtest")
                .build();

        Member savedFromMember = memberRepository.save(fromMember);

        Member savedToMember = memberRepository.save(toMember);

        Follow follow = Follow.createFollow(savedFromMember.getId(), savedToMember.getId());

        followRepository.save(follow);

        //when & then
        assertEquals(1L, followRepository.countByFromMember(savedFromMember.getId()));
    }

    @Test
    @DisplayName("Member Repo - 팔로워 수 테스트")
    void countByToMember() {
        //given
        Member fromMember = Member.builder()
                .email("test1234@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        Member toMember = Member.builder()
                .email("test12345@test.com")
                .nickname("test2")
                .password("testtest")
                .build();

        Member savedFromMember = memberRepository.save(fromMember);

        Member savedToMember = memberRepository.save(toMember);

        Follow follow = Follow.createFollow(savedFromMember.getId(), savedToMember.getId());

        followRepository.save(follow);

        //when & then
        assertEquals(1L, followRepository.countByToMember(savedToMember.getId()));
    }

    @Test
    @DisplayName("Member Cont - 닉네임 검색 테스트")
    void memberSearchByNickname() throws Exception {
        //given
        Member member = Member.builder()
                .email("test@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        UUID memberId = memberRepository.save(member).getId();

        Pageable pageable = Pageable.ofSize(10);

        //when
        Page<MemberListResponse> memberListResponses = memberRepository.findByNicknameLike(
                pageable, memberId, "tes");

        //then
        assertEquals(memberId, memberListResponses.getContent().get(0).getMemberId());
        assertEquals(member.getNickname(), memberListResponses.getContent().get(0).getNickname());
    }
}
