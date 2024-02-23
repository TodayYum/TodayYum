package com.todayyum.member;

import com.google.gson.Gson;
import com.todayyum.member.application.AddMemberUseCase;
import com.todayyum.member.application.FindMemberUseCase;
import com.todayyum.member.controller.MemberController;
import com.todayyum.member.dto.request.MemberAddRequest;
import com.todayyum.member.dto.response.MemberDetailResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Transactional
public class MemberControllerTest {

    @Mock
    private AddMemberUseCase addMemberUseCase;
    @Mock
    private FindMemberUseCase findMemberUseCase;
    @InjectMocks
    private MemberController memberController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    @DisplayName("Member Cont - 회원 가입 테스트")
    void addMember() throws Exception {

        //given
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
                .email("qwerasdf1234@naver.com")
                .nickname("bonnnkim")
                .password("a123456789")
                .build();

        when(addMemberUseCase.addMember(any(MemberAddRequest.class)))
                .thenReturn(UUID.randomUUID());

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(memberAddRequest)));

        //then
        resultActions.andExpect(status().isCreated());
    }

    @WithMockUser
    @Test
    @DisplayName("Member Cont - 회원 조회 테스트")
    void findMember() throws Exception {

        //given
        UUID memberId = UUID.randomUUID();

        MemberDetailResponse memberDetailResponse = MemberDetailResponse.builder()
                .memberId(memberId)
                .email("qwerasdf1234@naver.com")
                .nickname("bonnnnnkim")
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

}