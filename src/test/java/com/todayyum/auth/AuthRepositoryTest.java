package com.todayyum.auth;

import com.todayyum.auth.application.repository.VerificationCodeRepository;
import com.todayyum.auth.domain.VerificationCode;
import com.todayyum.global.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class AuthRepositoryTest {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
    @MockBean
    private CacheManager cacheManager;

    @Test
    @DisplayName("Auth Repo - 이메일 발송 테스트")
    public void sendVerificationCode() {
        //given
        String email = "test@test.com";
        String code = "123456";

        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(code)
                .build();

        //when
         verificationCode = verificationCodeRepository.save(verificationCode);

        //then
        assertEquals(verificationCode.getCode(), code);
        assertEquals(verificationCode.getEmail(), email);
    }

    @Test
    @DisplayName("Auth Repo - 이메일 검증 테스트")
    public void verifyVerificationCode() {
        //given
        String email = "test@test.com";
        String code = "123456";

        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(code)
                .build();

        verificationCodeRepository.save(verificationCode);

        //when
        verificationCode = verificationCodeRepository.findByEmail(email);

        //then
        assertEquals(email, verificationCode.getEmail());
        assertEquals(code, verificationCode.getCode());
    }

    @Test
    @DisplayName("Auth Repo - 이메일 검증 실패 테스트")
    public void verifyVerificationCodeFail() {
        //given
        String email = "test@test.com";
        String code = "123456";
        String wrongEmail = "test2@test.com";

        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(code)
                .build();

        verificationCodeRepository.save(verificationCode);

        //when&then
        assertThrows(CustomException.class, () -> {
            verificationCodeRepository.findByEmail(wrongEmail);
        });
    }
}
