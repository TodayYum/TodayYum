package com.todayyum.auth.controller;

import com.todayyum.auth.application.RefreshTokenUseCase;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/refresh")
    public ResponseEntity<?> tokenRefresh(@CookieValue(name = "refreshToken", required = false) Cookie cookie,
                                          @RequestParam Long memberId, HttpServletResponse response) {

        if(cookie == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        String accessToken = refreshTokenUseCase.refreshAccessToken(cookie.getValue(), memberId);
        response.addHeader("Authorization", "Bearer " + accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "refreshToken", required = false) Cookie cookie, HttpServletResponse response) {

        if(cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
