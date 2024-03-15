package com.todayyum.auth;

import com.todayyum.auth.application.RefreshTokenUseCase;
import com.todayyum.auth.application.RemoveTokenUseCase;
import com.todayyum.auth.application.VerifyEmailUseCase;
import com.todayyum.auth.controller.AuthController;
import com.todayyum.auth.dto.request.CodeVerifyRequest;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.member.WithMockMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockMember
public class AuthControllerTest {

    @MockBean
    private VerifyEmailUseCase verifyEmailUseCase;
    @MockBean
    private RefreshTokenUseCase refreshTokenUseCase;
    @MockBean
    private RemoveTokenUseCase removeTokenUseCase;
    @MockBean
    private JpaMetamodelMappingContext jpaMappingContext;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Auth Cont - 이메일 발송 테스트")
    public void sendVerificationCode() throws Exception {
        //given
        String email = "test@test.com";
        String code = "123456";

        when(verifyEmailUseCase.sendEmail(any(String.class)))
                .thenReturn(code);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/auth/verification-code")
                        .param("email", email));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.result")
                        .value(code))
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.CREATED.getMessage()));
    }

    @Test
    @DisplayName("Auth Cont - 이메일 검증 테스트")
    public void verifyVerificationCode() throws Exception {
        //given
        String email = "test@test.com";
        String code = "123456";

        when(verifyEmailUseCase.verifyCode(any(CodeVerifyRequest.class)))
                .thenReturn(true);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/auth/verification-code")
                        .param("email", email)
                        .param("code", code));

        //then
        resultActions.andExpect(
                status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Auth Cont - 이메일 검증 실패 테스트")
    public void verifyVerificationCodeFail() throws Exception {
        //given
        String email = "test@test.com";
        String code = "123456";

        when(verifyEmailUseCase.verifyCode(any(CodeVerifyRequest.class)))
                .thenReturn(false);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/auth/verification-code")
                        .param("email", email)
                        .param("code", code));

        //then
        resultActions.andExpect(
                status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.EMAIL_VERIFICATION_FAILED.getMessage()));
    }
}
