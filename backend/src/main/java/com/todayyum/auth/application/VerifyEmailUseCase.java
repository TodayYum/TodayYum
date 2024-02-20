package com.todayyum.auth.application;

import com.todayyum.auth.application.repository.VerificationCodeRepository;
import com.todayyum.auth.domain.VerificationCode;
import com.todayyum.auth.dto.request.CodeVerifyRequest;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerifyEmailUseCase {

    private final VerificationCodeRepository verificationCodeRepository;
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    @Transactional
    public void sendEmail(String email) {
        if(email == null || email.isEmpty()) {
            throw new CustomException(ResponseCode.EMPTY_INPUT);
        }

        if(memberRepository.existsByEmail(email)) {
            throw new CustomException(ResponseCode.DUPLICATE_EMAIL);
        }

        String code = generateVerificationCode();
        VerificationCode verificationCode = new VerificationCode(email, code);
        verificationCodeRepository.save(verificationCode);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("[TodayYum] 이메일 인증 번호입니다.");

            String body = "";

            body += "<div style=\"font-family: Verdana; padding: 0 30px 0 30px\">";
            body += "<h1> 이메일 인증 코드 안내 </h1>";
            body += "<p> 본 메일은 <span style=\"font-weight: 700\">TodayYum</span>의 이메일 인증을 위한 메일입니다. </p>";
            body += "<p> 아래의 [이메일 인증코드]를 입력하여 본인확인을 해주시기 바랍니다. </p>";
            body += "<div align=\"center\" style=\"background-color: #f3f4f8; height: 60px; font-size:" +
                    " 20px; font-weight: 700; display: flex; align-items: center;justify-content: center;\">";
            body += "<p>" + code + "</p>";
            body += "</div>";
            body += "<p>감사합니다.</p></div>";

            mimeMessageHelper.setText(body, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            verificationCodeRepository.deleteByEmail(email);
            throw new CustomException(ResponseCode.EMAIL_SEND_FAILED);
        }

    }

    @Transactional
    public boolean verifyCode(CodeVerifyRequest codeVerifyRequest) {
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(codeVerifyRequest.getEmail());

        if(!verificationCode.getCode().equals(codeVerifyRequest.getCode())) return false;

        verificationCodeRepository.deleteByEmail(verificationCode.getEmail());

        return true;
    }

    public String generateVerificationCode() {
        int random = (int) ((Math.random() * (999999 - 100000)) + 100000);

        return String.valueOf(random);
    }
}
