package com.todayyum.auth.controller;

import com.todayyum.auth.application.RefreshTokenUseCase;
import com.todayyum.auth.application.RemoveTokenUseCase;
import com.todayyum.auth.application.VerifyEmailUseCase;
import com.todayyum.auth.application.VerifyPasswordUseCase;
import com.todayyum.auth.dto.request.CodeVerifyRequest;
import com.todayyum.auth.dto.request.PasswordVerifyRequest;
import com.todayyum.auth.userDetails.CustomUserDetails;
import com.todayyum.global.dto.response.BaseResponse;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final RefreshTokenUseCase refreshTokenUseCase;
    private final RemoveTokenUseCase removeTokenUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final VerifyPasswordUseCase verifyPasswordUseCase;

    @PostMapping("/refresh")
    public ResponseEntity<?> tokenRefresh(@CookieValue(name = "refreshToken", required = false) Cookie cookie,
                                          @RequestParam UUID memberId, HttpServletResponse response) {

        if(cookie == null) {
            throw new CustomException(ResponseCode.REFRESH_TOKEN_MISSING);
        }

        String accessToken = refreshTokenUseCase.refreshAccessToken(cookie.getValue(), memberId);
        response.addHeader("Authorization", "Bearer " + accessToken);

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "refreshToken", required = false) Cookie cookie,
                                    HttpServletResponse response) {

        if(cookie == null) {
            return BaseResponse.createResponseEntity(ResponseCode.REFRESH_TOKEN_MISSING);
        }

        cookie.setMaxAge(0);
        response.addCookie(cookie);
        removeTokenUseCase.removeRefreshToken(cookie.getValue());

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PostMapping("/verification-code")
    public ResponseEntity<?> sendVerificationCode(@RequestParam String email) {
        return BaseResponse.createResponseEntity(ResponseCode.CREATED, verifyEmailUseCase.sendEmail(email));
    }

    @PostMapping("/verify/verification-code")
    public ResponseEntity<?> verifyVerificationCode(@Valid CodeVerifyRequest codeVerifyRequest) {
        boolean result = verifyEmailUseCase.verifyCode(codeVerifyRequest);

        if(result) return BaseResponse.createResponseEntity(ResponseCode.OK);

        return BaseResponse.createResponseEntity(ResponseCode.EMAIL_VERIFICATION_FAILED);
    }

    @PostMapping("/verify/password")
    public ResponseEntity<?> verifyPassword(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @Valid PasswordVerifyRequest passwordVerifyRequest) {
        passwordVerifyRequest.setMemberId(customUserDetails.getMemberId());

        boolean result = verifyPasswordUseCase.verifyPassword(passwordVerifyRequest);

        if(result) return BaseResponse.createResponseEntity(ResponseCode.OK);

        return BaseResponse.createResponseEntity(ResponseCode.LOGIN_ERROR_CREDENTIALS_INVALID);
    }
}
