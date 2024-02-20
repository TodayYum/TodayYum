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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void init() {
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
                .email("qwerasdf1234@naver.com")
                .nickname("bonnnnnkim")
                .password(bCryptPasswordEncoder.encode("a123456789"))
                .build();

        Member member = Member.createMember(memberAddRequest);

        memberRepository.save(member);
    }

    @Test
    @DisplayName("Auth - 로그인 테스트")
    public void login() throws Exception {

        //given
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", "qwerasdf1234@naver.com");
        formData.add("password", "a123456789");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/auth/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(formData));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Authorization"))
                .andExpect(MockMvcResultMatchers.cookie().exists("refreshToken"));
    }

    @Test
    @DisplayName("Auth - 로그아웃 테스트")
    public void logout() throws Exception {

        //given & when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/logout")
                .cookie(new Cookie("refreshToken", "tokenValue")));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(response -> {
                    Cookie cookie = response.getResponse().getCookie("refreshToken");
                    assert cookie != null;
                    assert cookie.getMaxAge() == 0; // 쿠키의 만료시간이 0인지 확인
                });
    }

}
