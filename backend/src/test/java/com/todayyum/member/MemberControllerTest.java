package com.todayyum.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.*;
import com.todayyum.member.controller.MemberController;
import com.todayyum.member.domain.ValidationResult;
import com.todayyum.member.dto.request.*;
import com.todayyum.member.dto.response.MemberDetailResponse;
import com.todayyum.member.dto.response.MemberListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockMember
public class MemberControllerTest {

    @MockBean
    private AddMemberUseCase addMemberUseCase;
    @MockBean
    private FindMemberUseCase findMemberUseCase;
    @MockBean
    private RemoveMemberUseCase removeMemberUseCase;
    @MockBean
    private ModifyMemberUseCase modifyMemberUseCase;
    @MockBean
    private AddFollowUseCase addFollowUseCase;
    @MockBean
    private RemoveFollowUseCase removeFollowUseCase;
    @MockBean
    private JpaMetamodelMappingContext jpaMappingContext;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Member Cont - 회원 가입 테스트")
    void addMember() throws Exception {
        //given
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", "test@test.com");
        formData.add("nickname", "test");
        formData.add("password", "a123456789");

        when(addMemberUseCase.addMember(any(MemberAddRequest.class)))
                .thenReturn(UUID.randomUUID());

        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/members")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(formData));

        //then
        resultActions
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Member Cont - 회원 가입 실패 테스트(입력 오류)")
    void addMemberFailByInput() throws Exception {
        //given
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("nickname", "test");
        formData.add("password", "a123456789");

        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/members")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formData));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].message")
                .value("이메일을 입력해주세요."));
    }

    @Test
    @DisplayName("Member Cont - 회원 조회 테스트")
    void findMember() throws Exception {
        //given
        UUID memberId = UUID.fromString("c3f1843f-1304-4526-ba5b-ffedc85f0d55");

        MemberDetailResponse memberDetailResponse = MemberDetailResponse.builder()
                .memberId(memberId)
                .email("test@test.com")
                .nickname("test")
                .build();

        when(findMemberUseCase.findMember(memberId, memberId))
                .thenReturn(memberDetailResponse);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/{memberId}", memberId));

        //then
        resultActions.andExpect(
                status().isOk())
                .andExpect(jsonPath("$.result.memberId")
                        .value(memberDetailResponse.getMemberId().toString()))
                .andExpect(jsonPath("$.result.email")
                        .value(memberDetailResponse.getEmail()))
                .andExpect(jsonPath("$.result.nickname")
                        .value(memberDetailResponse.getNickname()));
    }

    @Test
    @DisplayName("Member Cont - 회원 조회 실패 테스트(멤버 식별자 오류)")
    void findMemberFailByMemberId() throws Exception {
        //given
        UUID memberId = UUID.fromString("c3f1843f-1304-4526-ba5b-ffedc85f0d55");

        when(findMemberUseCase.findMember(any(UUID.class), any(UUID.class)))
                .thenThrow(new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/{id}", memberId));

        //then
        resultActions.andExpect(
                status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.MEMBER_ID_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 회원 탈퇴 테스트")
    void removeMember() throws Exception {
        //given
        doNothing()
                .when(removeMemberUseCase)
                .removeMember(any(UUID.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/members"));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 닉네임 변경 테스트")
    void modifyNickname() throws Exception {
        //given
        NicknameModifyRequest nicknameModifyRequest = NicknameModifyRequest.builder()
                .nickname("yonggkimm")
                .build();

        doNothing()
                .when(modifyMemberUseCase)
                .modifyNickname(any(NicknameModifyRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/members/nicknames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(nicknameModifyRequest)));

        //then
        resultActions.andExpect(
                status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 닉네임 변경 실패 테스트(입력 오류)")
    void modifyNicknameFailByInput() throws Exception {
        //given
        NicknameModifyRequest nicknameModifyRequest = NicknameModifyRequest.builder()
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/members/nicknames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(nicknameModifyRequest)));

        //then
        resultActions.andExpect(
                        status().isBadRequest())
                .andExpect(jsonPath("$.[0].message")
                        .value("닉네임을 입력해주세요."));
    }

    @Test
    @DisplayName("Member Cont - 소개글 변경 테스트")
    void modifyIntroduction() throws Exception {
        //given
        IntroductionModifyRequest introductionModifyRequest = IntroductionModifyRequest.builder()
                .introduction("내가 누구?")
                .build();

        doNothing()
                .when(modifyMemberUseCase)
                .modifyIntroduction(any(IntroductionModifyRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/members/introductions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(introductionModifyRequest)));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 소개글 변경 실패 테스트(입력 오류)")
    void modifyIntroductionFailByInput() throws Exception {
        //given
        IntroductionModifyRequest introductionModifyRequest = IntroductionModifyRequest.builder()
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/members/introductions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(introductionModifyRequest)));

        //then
        resultActions.andExpect(
                        status().isBadRequest())
                .andExpect(jsonPath("$.[0].message")
                        .value("소개글을 입력해주세요."));
    }

    @Test
    @DisplayName("Member Cont - 비밀번호 변경 테스트")
    void modifyPassword() throws Exception {
        //given
        PasswordModifyRequest passwordModifyRequest = PasswordModifyRequest.builder()
                .password("test12345")
                .email("test@test.com")
                .build();

        doNothing()
                .when(modifyMemberUseCase)
                .modifyPassword(any(PasswordModifyRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/members/passwords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passwordModifyRequest)));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 비밀번호 변경 실패 테스트(입력 오류)")
    void modifyPasswordsFailByInput() throws Exception {
        //given
        PasswordModifyRequest passwordModifyRequest = PasswordModifyRequest.builder()
                .email("test@test.com")
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/members/passwords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passwordModifyRequest)));

        //then
        resultActions.andExpect(
                        status().isBadRequest())
                .andExpect(jsonPath("$.[0].message")
                        .value("비밀번호를 입력해주세요."));
    }

    @Test
    @DisplayName("Member Cont - 프로필 변경 테스트")
    void modifyProfile() throws Exception {
        //given
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        doNothing()
                .when(modifyMemberUseCase)
                .modifyProfile(any(ProfileModifyRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.multipart("/api/members/profiles")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formData));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 닉네임 검증 테스트")
    void validateNickname() throws Exception {
        //given
        String nickname = "yonggkimm";

        when(findMemberUseCase.validateNickname(any(String.class)))
                .thenReturn(ValidationResult.VALID);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/nicknames/validations")
                        .param("nickname", nickname));

        //then
        resultActions.andExpect(
                status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.VALID_NICKNAME.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 닉네임 검증 실패 테스트(입력 오류)")
    void validateNicknameFailByInput() throws Exception {
        //given
        String nickname = "";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/nicknames/validations")
                        .param("nickname", nickname));

        //then
        resultActions.andExpect(
                        status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.EMPTY_INPUT.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 이메일 검증 테스트")
    void validateEmail() throws Exception {
        //given
        String email = "test@test.com";

        when(findMemberUseCase.validateEmail(any(String.class)))
                .thenReturn(ValidationResult.VALID);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/emails/validations")
                        .param("email", email));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.VALID_EMAIL.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 이메일 검증 실패 테스트(입력 오류)")
    void validateEmailFailByInput() throws Exception {
        //given
        String email = "";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/emails/validations")
                        .param("email", email));

        //then
        resultActions.andExpect(
                        status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.EMPTY_INPUT.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 팔로우 테스트")
    void addFollow() throws Exception {
        //given
        Long id = 100000L;

        when(addFollowUseCase.addFollow(any(UUID.class), any(UUID.class)))
                .thenReturn(id);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/members/{memberId}/follow", UUID.randomUUID()));

        //then
        resultActions.andExpect(
                        status().isCreated())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.CREATED.getMessage()))
                .andExpect(jsonPath("$.result")
                        .value(id));
    }

    @Test
    @DisplayName("Member Cont - 팔로우 실패 테스트(중복 오류)")
    void addFollowFailByDuplication() throws Exception {
        //given
        when(addFollowUseCase.addFollow(any(UUID.class), any(UUID.class)))
                .thenThrow(new CustomException(ResponseCode.DUPLICATE_FOLLOW));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/members/{memberId}/follow", UUID.randomUUID()));

        //then
        resultActions.andExpect(
                        status().isConflict())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.DUPLICATE_FOLLOW.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 언팔로우 테스트")
    void removeFollow() throws Exception {
        //given
        doNothing()
                .when(removeFollowUseCase)
                        .removeFollow(any(UUID.class), any(UUID.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/members/{memberId}/follow", UUID.randomUUID()));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 언팔로우 실패 테스트(팔로우 X 오류)")
    void removeFollowFailByNotFollowing() throws Exception {
        //given
        doThrow(new CustomException(ResponseCode.NOT_FOLLOW))
                .when(removeFollowUseCase)
                .removeFollow(any(UUID.class), any(UUID.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/members/{memberId}/follow", UUID.randomUUID()));

        //then
        resultActions.andExpect(
                        status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.NOT_FOLLOW.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 팔로잉 리스트 테스트")
    void listFollowing() throws Exception {
        //given
        UUID memberId = UUID.fromString("c3f1843f-1304-4526-ba5b-ffedc85f0d55");
        String nickname = "test";
        String profile = "test.jpg";
        String introduction = "hi";

        MemberListResponse memberListResponse = new MemberListResponse(memberId, nickname, profile, introduction);

        List<MemberListResponse> memberListResponseList = new ArrayList<>();
        memberListResponseList.add(memberListResponse);
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<MemberListResponse> memberListResponses = new PageImpl<>(memberListResponseList, pageRequest, 1);

        when(findMemberUseCase.listFollowing(any(Pageable.class), eq(memberId), eq(memberId)))
                .thenReturn(memberListResponses);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/{memberId}/followings", memberId)
                        .param("page", "0"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()))
                .andExpect(jsonPath("$.result.content.[0].memberId")
                        .value(memberId.toString()))
                .andExpect(jsonPath("$.result.content.[0].nickname")
                        .value(nickname))
                .andExpect(jsonPath("$.result.content.[0].profile")
                        .value(profile));
    }

    @Test
    @DisplayName("Member Cont - 팔로잉 리스트 실패 테스트(멤버 식별자 오류)")
    void listFollowingFailByMemberId() throws Exception {
        //given
        when(findMemberUseCase.listFollowing(any(Pageable.class), any(UUID.class) ,any(UUID.class)))
                .thenThrow(new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/{memberId}/followings", UUID.randomUUID()));

        //then
        resultActions.andExpect(
                        status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.MEMBER_ID_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 팔로워 리스트 테스트")
    void listFollower() throws Exception {
        //given
        UUID memberId = UUID.fromString("c3f1843f-1304-4526-ba5b-ffedc85f0d55");
        String nickname = "test";
        String profile = "test.jpg";
        String introduction = "hi";

        MemberListResponse memberListResponse = new MemberListResponse(memberId, nickname, profile, introduction);

        List<MemberListResponse> memberListResponseList = new ArrayList<>();
        memberListResponseList.add(memberListResponse);
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<MemberListResponse> memberListResponses = new PageImpl<>(memberListResponseList, pageRequest, 1);

        when(findMemberUseCase.listFollower(any(Pageable.class), eq(memberId), eq(memberId)))
                .thenReturn(memberListResponses);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/{memberId}/followers", memberId)
                        .param("page", "0"));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()))
                .andExpect(jsonPath("$.result.content.[0].memberId")
                        .value(memberId.toString()))
                .andExpect(jsonPath("$.result.content.[0].nickname")
                        .value(nickname))
                .andExpect(jsonPath("$.result.content.[0].profile")
                        .value(profile));
    }

    @Test
    @DisplayName("Member Cont - 팔로워 리스트 실패 테스트(멤버 식별자 오류)")
    void listFollowerFailByMemberId() throws Exception {
        //given
        when(findMemberUseCase.listFollower(any(Pageable.class), any(UUID.class), any(UUID.class)))
                .thenThrow(new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/{memberId}/followers", UUID.randomUUID()));

        //then
        resultActions.andExpect(
                        status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.MEMBER_ID_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 닉네임 검색 테스트")
    void memberSearchByNickname() throws Exception {
        //given
        UUID memberId = UUID.fromString("c3f1843f-1304-4526-ba5b-ffedc85f0d55");
        String nickname = "test";
        String profile = "test.jpg";
        String introduction = "hi";

        MemberListResponse memberListResponse = new MemberListResponse(memberId, nickname, profile, introduction);

        List<MemberListResponse> memberListResponseList = new ArrayList<>();
        memberListResponseList.add(memberListResponse);
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<MemberListResponse> memberListResponses = new PageImpl<>(memberListResponseList, pageRequest, 1);

        when(findMemberUseCase.findListByNickname(any(Pageable.class), eq(memberId), eq("tes")))
                .thenReturn(memberListResponses);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/search")
                        .param("page", "0").param("nickname", "tes"));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()))
                .andExpect(jsonPath("$.result.content.[0].memberId")
                        .value(memberId.toString()))
                .andExpect(jsonPath("$.result.content.[0].nickname")
                        .value(nickname))
                .andExpect(jsonPath("$.result.content.[0].profile")
                        .value(profile));
    }

}