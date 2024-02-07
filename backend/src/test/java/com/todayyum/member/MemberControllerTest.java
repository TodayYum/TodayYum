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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

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
                .nickname("bonnnnnkim")
                .password("a123456789")
                .build();

        Mockito.when(addMemberUseCase.addMember(Mockito.any(MemberAddRequest.class)))
                .thenReturn(1000000L);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(memberAddRequest)));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithMockUser
    @Test
    @DisplayName("Member Cont - 회원 조회 테스트")
    void findMember() throws Exception {

        //given
        MemberDetailResponse memberDetailResponse = MemberDetailResponse.builder()
                .memberId(1000000L)
                .email("qwerasdf1234@naver.com")
                .nickname("bonnnnnkim")
                .build();

        Mockito.when(findMemberUseCase.findMember(1000000L))
                .thenReturn(memberDetailResponse);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/members/{id}", 1000000L)
        );

        //then
        resultActions.andExpect(
                MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.memberId")
                        .value(memberDetailResponse.getMemberId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value(memberDetailResponse.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname")
                        .value(memberDetailResponse.getNickname()));
    }

}