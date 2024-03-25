package com.todayyum.member;

import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.global.util.S3Util;
import com.todayyum.member.application.*;
import com.todayyum.member.application.repository.FollowRepository;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Follow;
import com.todayyum.member.domain.Member;
import com.todayyum.member.domain.ValidationResult;
import com.todayyum.member.dto.request.*;
import com.todayyum.member.dto.response.FollowListResponse;
import com.todayyum.member.dto.response.MemberDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Transactional
public class MemberUseCaseTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private FollowRepository followRepository;
    @InjectMocks
    private AddMemberUseCase addMemberUseCase;
    @InjectMocks
    private FindMemberUseCase findMemberUseCase;
    @InjectMocks
    private ModifyMemberUseCase modifyMemberUseCase;
    @InjectMocks
    private RemoveMemberUseCase removeMemberUseCase;
    @InjectMocks
    private AddFollowUseCase addFollowUseCase;
    @InjectMocks
    private FindFollowUseCase findFollowUseCase;
    @InjectMocks
    private RemoveFollowUseCase removeFollowUseCase;
    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private S3Util s3Util;

    @Test
    @DisplayName("Member UC - 회원 가입 테스트")
    void addMember() {
        //given
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
                .email("test@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        Member member = Member.createMember(memberAddRequest);
        UUID uuid = UUID.randomUUID();
        member.changeId(uuid);

        when(memberRepository.save(any(Member.class)))
                .thenReturn(member);

        //when
        UUID memberId = addMemberUseCase.addMember(memberAddRequest);

        //then
        assertEquals(memberId, uuid);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("Member UC - 회원 가입 실패 테스트(닉네임 중복 오류)")
    void addMemberFailByDuplicateNickname() {
        //given
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
                .email("test@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        when(memberRepository.existsByNickname(any(String.class)))
                .thenReturn(true);

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> addMemberUseCase.addMember(memberAddRequest));
        assertEquals(ResponseCode.DUPLICATE_NICKNAME, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member UC - 회원 가입 실패 테스트(이메일 중복 오류)")
    void addMemberFailByDuplicateEmail() {
        //given
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
                .email("test@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        when(memberRepository.existsByEmail(any(String.class)))
                .thenReturn(true);

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> addMemberUseCase.addMember(memberAddRequest));
        assertEquals(ResponseCode.DUPLICATE_EMAIL, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member UC - 회원 조회 테스트")
    void findMember() {
        //given
        UUID memberId = UUID.randomUUID();

        Member member = Member.builder()
                .id(memberId)
                .email("test@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        when(memberRepository.findById(memberId))
                .thenReturn(member);

        //when
        MemberDetailResponse memberDetailResponse = findMemberUseCase.findMember(memberId);

        //then
        assertEquals(member.getEmail(), memberDetailResponse.getEmail());
        assertEquals(member.getNickname(), memberDetailResponse.getNickname());

        verify(memberRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    @DisplayName("Member UC - 회원 조회 실패 테스트(멤버 식별자 오류)")
    void findMemberFailByMemberId() {
        //given
        UUID memberId = UUID.randomUUID();

        when(memberRepository.findById(memberId))
                .thenThrow(new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> findMemberUseCase.findMember(memberId));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());

        verify(memberRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    @DisplayName("Member UC - 회원 탈퇴 테스트")
    void removeMember() {
        //given
        UUID memberId = UUID.randomUUID();

        doNothing()
                .when(memberRepository)
                .deleteById(any(UUID.class));

        //when
        removeMemberUseCase.removeMember(memberId);

        //then
        verify(memberRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Member UC - 회원 탈퇴 실패 테스트(멤버 식별자 오류)")
    void removeMemberFailByMemberId() {
        //given
        UUID memberId = UUID.randomUUID();

        doThrow(new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND))
                .when(memberRepository)
                        .deleteById(any(UUID.class));

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> removeMemberUseCase.removeMember(memberId));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());

        verify(memberRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Member UC - 닉네임 변경 테스트")
    void modifyNickname() {
        //given
        UUID memberId = UUID.randomUUID();

        NicknameModifyRequest nicknameModifyRequest = NicknameModifyRequest.builder()
                .nickname("yonggkim")
                .memberId(memberId)
                .build();

        Member member = Member.builder()
                .nickname("bonkim")
                .build();

        when(memberRepository.findById(memberId))
                .thenReturn(member);

        when(memberRepository.existsByNickname(any(String.class)))
                .thenReturn(false);

        //when
        modifyMemberUseCase.modifyNickname(nicknameModifyRequest);

        //then
        assertEquals(nicknameModifyRequest.getNickname(), member.getNickname());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("Member UC - 닉네임 변경 실패 테스트(닉네임 중복 오류)")
    void modifyNicknameFailByDuplication() {
        //given
        UUID memberId = UUID.randomUUID();

        NicknameModifyRequest nicknameModifyRequest = NicknameModifyRequest.builder()
                .nickname("yonggkim")
                .memberId(memberId)
                .build();

        Member member = Member.builder()
                .nickname("bonkim")
                .build();

        when(memberRepository.existsByNickname(any(String.class)))
                .thenReturn(true);

        when(memberRepository.findById(memberId))
                .thenReturn(member);

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> modifyMemberUseCase.modifyNickname(nicknameModifyRequest));
        assertEquals(ResponseCode.DUPLICATE_NICKNAME, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member UC - 비밀번호 변경 테스트")
    void modifyPassword() {
        //given
        UUID memberId = UUID.randomUUID();

        PasswordModifyRequest passwordModifyRequest = PasswordModifyRequest.builder()
                .password("testtest2")
                .memberId(memberId)
                .build();

        Member member = Member.builder()
                .password("testtest")
                .build();

        when(memberRepository.findById(memberId))
                .thenReturn(member);

        //when
        modifyMemberUseCase.modifyPassword(passwordModifyRequest);

        //then
        assertTrue(bCryptPasswordEncoder.matches(passwordModifyRequest.getPassword(), member.getPassword()));
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("Member UC - 소개글 변경 테스트")
    void modifyIntroduction() {
        //given
        UUID memberId = UUID.randomUUID();

        IntroductionModifyRequest introductionModifyRequest = IntroductionModifyRequest.builder()
                .introduction("내가 누구??")
                .memberId(memberId)
                .build();

        Member member = Member.builder()
                .introduction("내가 누구?")
                .build();

        when(memberRepository.findById(memberId))
                .thenReturn(member);

        //when
        modifyMemberUseCase.modifyIntroduction(introductionModifyRequest);

        //then
        assertEquals(introductionModifyRequest.getIntroduction(), member.getIntroduction());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("Member UC - 프로필 변경 테스트")
    void modifyProfile() {
        //given
        UUID memberId = UUID.randomUUID();

        ProfileModifyRequest profileModifyRequest = ProfileModifyRequest.builder()
                .memberId(memberId)
                .profile(null)
                .build();

        Member member = Member.builder()
                .build();

        when(memberRepository.findById(memberId))
                .thenReturn(member);

        when(s3Util.uploadFile(null))
                .thenReturn("프로필사진");

        //when
        modifyMemberUseCase.modifyProfile(profileModifyRequest);

        //then
        assertEquals("프로필사진", member.getProfile());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("Member UC - 이메일 검증 테스트")
    void validateEmail() {
        //given
        String email = "test@test.com";

        when(memberRepository.existsByEmail(email))
                .thenReturn(false);

        //when & then
        assertEquals(ValidationResult.VALID, findMemberUseCase.validateEmail(email));
    }

    @Test
    @DisplayName("Member UC - 이메일 정규식 검증 테스트")
    void validateInvalidEmail() {
        //given
        String email = "test@test";

        //when & then
        assertEquals(ValidationResult.INVALID, findMemberUseCase.validateEmail(email));
    }

    @Test
    @DisplayName("Member UC - 이메일 중복 검증 테스트")
    void validateDuplicateEmail() {
        //given
        String email = "test@test.com";

        when(memberRepository.existsByEmail(email))
                .thenReturn(true);

        //when & then
        assertEquals(ValidationResult.DUPLICATED, findMemberUseCase.validateEmail(email));
    }

    @Test
    @DisplayName("Member UC - 닉네임 검증 테스트")
    void validateNickname() {
        //given
        String nickname = "yonggkim";

        when(memberRepository.existsByNickname(nickname))
                .thenReturn(false);

        //when & then
        assertEquals(ValidationResult.VALID, findMemberUseCase.validateNickname(nickname));
    }

    @Test
    @DisplayName("Member UC - 닉네임 길이 검증 테스트")
    void validateInvalidNickname() {
        //given
        String nickname = "yonggkimmmmm";

        //when & then
        assertEquals(ValidationResult.INVALID, findMemberUseCase.validateNickname(nickname));
    }

    @Test
    @DisplayName("Member UC - 닉네임 중복 검증 테스트")
    void validateDuplicateNickname() {
        //given
        String nickname = "yonggkim";

        when(memberRepository.existsByNickname(nickname))
                .thenReturn(true);

        //when & then
        assertEquals(ValidationResult.DUPLICATED, findMemberUseCase.validateNickname(nickname));
    }

    @Test
    @DisplayName("Member UC - 팔로우 테스트")
    void addFollow() {
        //given
        UUID fromMemberId = UUID.randomUUID();
        UUID toMemberId = UUID.randomUUID();
        Long id = 123456789L;

        Follow follow = Follow.createFollow(fromMemberId, toMemberId);
        follow.changeId(id);

        when(followRepository.save(any(Follow.class)))
                .thenReturn(follow);

        //when
        Long savedId = addFollowUseCase.addFollow(fromMemberId, toMemberId);

        //then
        assertEquals(id, savedId);
        verify(followRepository, times(1)).save(any(Follow.class));
    }

    @Test
    @DisplayName("Member UC - 팔로우 실패 테스트(중복 오류)")
    void addFollowFailByDuplication() {
        //given
        UUID fromMemberId = UUID.randomUUID();
        UUID toMemberId = UUID.randomUUID();

        when(followRepository.existsByFromMemberAndToMember(any(Follow.class)))
                .thenReturn(true);

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> addFollowUseCase.addFollow(fromMemberId, toMemberId));
        assertEquals(ResponseCode.DUPLICATE_FOLLOW, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member UC - 언팔로우 테스트")
    void removeFollow() {
        //given
        UUID fromMemberId = UUID.randomUUID();
        UUID toMemberId = UUID.randomUUID();

        doNothing()
                .when(followRepository)
                .deleteByFromMemberAndToMember(any(Follow.class));

        when(followRepository.existsByFromMemberAndToMember(any(Follow.class)))
                .thenReturn(true);

        //when
        removeFollowUseCase.removeFollow(fromMemberId, toMemberId);

        //then
        verify(followRepository, times(1)).deleteByFromMemberAndToMember(any(Follow.class));
    }

    @Test
    @DisplayName("Member UC - 언팔로우 실패 테스트(팔로우 X 오류)")
    void removeFollowFailByNotFollow() {
        //given
        UUID fromMemberId = UUID.randomUUID();
        UUID toMemberId = UUID.randomUUID();

        when(followRepository.existsByFromMemberAndToMember(any(Follow.class)))
                .thenReturn(false);

        //when & then
        CustomException thrown = assertThrows(CustomException.class,
                () -> removeFollowUseCase.removeFollow(fromMemberId, toMemberId));
        assertEquals(ResponseCode.NOT_FOLLOW, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member UC - 팔로잉 리스트 테스트")
    void listFollowing() {
        //given
        UUID memberId = UUID.randomUUID();
        String nickname = "test";
        String profile = "test.jpg";

        FollowListResponse followListResponse = FollowListResponse.builder()
                .memberId(memberId)
                .nickname(nickname)
                .profile(profile)
                .build();

        List<FollowListResponse> followListResponses = new ArrayList<>();
        followListResponses.add(followListResponse);

        UUID fromMemberId = UUID.randomUUID();

        when(followRepository.findByFromMember(any(UUID.class)))
                .thenReturn(followListResponses);

        //when
        List<FollowListResponse> savedFollowListResponses = findFollowUseCase.listFollowing(fromMemberId);

        //then
        assertEquals(followListResponses.get(0).getMemberId(), savedFollowListResponses.get(0).getMemberId());
        verify(followRepository, times(1)).findByFromMember(any(UUID.class));
    }

    @Test
    @DisplayName("Member UC - 팔로잉 리스트 실패 테스트(멤버 식별자 오류)")
    void listFollowingFailByMemberId() {
        //given
        UUID fromMemberId = UUID.randomUUID();

        when(followRepository.findByFromMember(any(UUID.class)))
                .thenThrow(new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        //when&&then
        CustomException thrown = assertThrows(CustomException.class,
                () -> findFollowUseCase.listFollowing(fromMemberId));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Member UC - 팔로워 리스트 테스트")
    void listFollower() {
        //given
        UUID memberId = UUID.randomUUID();
        String nickname = "test";
        String profile = "test.jpg";

        FollowListResponse followListResponse = FollowListResponse.builder()
                .memberId(memberId)
                .nickname(nickname)
                .profile(profile)
                .build();

        List<FollowListResponse> followListResponses = new ArrayList<>();
        followListResponses.add(followListResponse);

        UUID toMemberId = UUID.randomUUID();

        when(followRepository.findByToMember(any(UUID.class)))
                .thenReturn(followListResponses);

        //when
        List<FollowListResponse> savedFollowListResponses = findFollowUseCase.listFollower(toMemberId);

        //then
        assertEquals(followListResponses.get(0).getMemberId(), savedFollowListResponses.get(0).getMemberId());
        verify(followRepository, times(1)).findByToMember(any(UUID.class));
    }

    @Test
    @DisplayName("Member UC - 팔로워 리스트 실패 테스트(멤버 식별자 오류)")
    void listFollowerFailByMemberId() {
        //given
        UUID toMemberId = UUID.randomUUID();

        when(followRepository.findByToMember(any(UUID.class)))
                .thenThrow(new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        //when&&then
        CustomException thrown = assertThrows(CustomException.class,
                () -> findFollowUseCase.listFollower(toMemberId));
        assertEquals(ResponseCode.MEMBER_ID_NOT_FOUND, thrown.getResponseCode());
    }
}
