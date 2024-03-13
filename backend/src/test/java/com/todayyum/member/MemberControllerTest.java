package com.todayyum.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.AddMemberUseCase;
import com.todayyum.member.application.FindMemberUseCase;
import com.todayyum.member.application.ModifyMemberUseCase;
import com.todayyum.member.application.RemoveMemberUseCase;
import com.todayyum.member.controller.MemberController;
import com.todayyum.member.domain.ValidationResult;
import com.todayyum.member.dto.request.*;
import com.todayyum.member.dto.response.MemberDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
        UUID memberId = UUID.randomUUID();

        MemberDetailResponse memberDetailResponse = MemberDetailResponse.builder()
                .memberId(memberId)
                .email("test@test.com")
                .nickname("test")
                .build();

        when(findMemberUseCase.findMember(memberId))
                .thenReturn(memberDetailResponse);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/{id}", memberId));

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
        UUID memberId = UUID.randomUUID();

        when(findMemberUseCase.findMember(any(UUID.class)))
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
    void modifyComment() throws Exception {

        //given
        CommentModifyRequest commentModifyRequest = CommentModifyRequest.builder()
                .comment("내가 누구?")
                .build();

        doNothing()
                .when(modifyMemberUseCase)
                .modifyComment(any(CommentModifyRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/members/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentModifyRequest)));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Member Cont - 소개글 변경 실패 테스트(입력 오류)")
    void modifyCommentFailByInput() throws Exception {
        //given
        CommentModifyRequest commentModifyRequest = CommentModifyRequest.builder()
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/members/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentModifyRequest)));

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
                .memberId(UUID.randomUUID())
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

}