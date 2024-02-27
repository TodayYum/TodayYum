package com.todayyum.auth;

import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.request.MemberAddRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoginLogoutTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void init() {
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
                .email("test@test.com")
                .nickname("test")
                .password(bCryptPasswordEncoder.encode("a123456789"))
                .build();

        Member member = Member.createMember(memberAddRequest);

        memberRepository.save(member);
    }

    @Test
    @DisplayName("Login/out - 로그인 테스트")
    public void login() throws Exception {

        //given
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", "test@test.com");
        formData.add("password", "a123456789");

        //when
        ResultActions resultActions = mockMvc.perform(multipart("/api/auth/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(formData));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(cookie().exists("refreshToken"));
    }

    @Test
    @DisplayName("Login/out - 로그인 실패 테스트(이메일 오류)")
    public void loginFailByEmail() throws Exception {

        //given
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", "test2@test.com");
        formData.add("password", "a123456789");

        //when
        ResultActions resultActions = mockMvc.perform(multipart("/api/auth/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(formData));

        //then
        resultActions.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message")
                        .value("로그인 정보가 올바르지 않습니다."));
    }

    @Test
    @DisplayName("Login/out - 로그인 실패 테스트(비밀번호 오류)")
    public void loginFailByPassword() throws Exception {

        //given
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", "test@test.com");
        formData.add("password", "a12345678");

        //when
        ResultActions resultActions = mockMvc.perform(multipart("/api/auth/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(formData));

        //then
        resultActions.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message")
                        .value("로그인 정보가 올바르지 않습니다."));
    }

    @Test
    @DisplayName("Login/out - 로그아웃 테스트")
    public void logout() throws Exception {

        //given
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", "test@test.com");
        formData.add("password", "a123456789");

        Cookie refreshToken = mockMvc.perform(multipart("/api/auth/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(formData))
                .andReturn()
                .getResponse()
                .getCookie("refreshToken");;

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/auth/logout")
                .cookie(new Cookie("refreshToken", refreshToken.getValue())));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(response -> {
                    Cookie cookie = response.getResponse().getCookie("refreshToken");
                    assert cookie != null;
                    assert cookie.getMaxAge() == 0;
                });
    }

}
