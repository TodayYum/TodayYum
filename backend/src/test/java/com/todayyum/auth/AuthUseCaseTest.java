package com.todayyum.auth;

import com.todayyum.auth.application.VerifyEmailUseCase;
import com.todayyum.auth.application.repository.VerificationCodeRepository;
import com.todayyum.auth.domain.VerificationCode;
import com.todayyum.auth.dto.request.CodeVerifyRequest;
import com.todayyum.member.application.repository.MemberRepository;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
public class AuthUseCaseTest {

    @Mock
    private VerificationCodeRepository verificationCodeRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private VerifyEmailUseCase verifyEmailUseCase;

    @Test
    @DisplayName("Auth UC - 이메일 발송 테스트")
    public void sendVerificationCode() {
        //given
        String email = "test@test.com";
        String code = "123456";

        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(code)
                .build();

        when(verificationCodeRepository.save(any(VerificationCode.class)))
                .thenReturn(verificationCode);
        when(javaMailSender.createMimeMessage())
                .thenReturn(new MimeMessage((Session) null));

        //when
        String savedCode = verifyEmailUseCase.sendEmail(email);

        //then
        assertEquals(code, savedCode);
    }

    @Test
    @DisplayName("Auth UC - 이메일 검증 테스트")
    public void verifyVerificationCode() {
        //given
        String email = "test@test.com";
        String code = "123456";

        CodeVerifyRequest codeVerifyRequest = CodeVerifyRequest.builder()
                .email(email)
                .code(code)
                .build();

        VerificationCode verificationCode = VerificationCode.createVerificationCode(codeVerifyRequest);

        when(verificationCodeRepository.findByEmail(any(String.class)))
                .thenReturn(verificationCode);

        //when
        boolean result = verifyEmailUseCase.verifyCode(codeVerifyRequest);

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Auth UC - 이메일 검증 실패 테스트")
    public void verifyVerificationCodeFail() {
        //given
        String email = "test@test.com";
        String code = "123456";

        CodeVerifyRequest codeVerifyRequest = CodeVerifyRequest.builder()
                .email(email)
                .code(code)
                .build();

        VerificationCode verificationCode = VerificationCode.createVerificationCode(codeVerifyRequest);
        verificationCode.changeCode("123457");

        when(verificationCodeRepository.findByEmail(any(String.class)))
                .thenReturn(verificationCode);

        //when
        boolean result = verifyEmailUseCase.verifyCode(codeVerifyRequest);

        //then
        assertFalse(result);
    }
}
