package com.todayyum.auth.application;

import com.todayyum.auth.application.repository.TokenRepository;
import com.todayyum.auth.domain.Token;
import com.todayyum.auth.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final TokenRepository tokenRepository;
    private final JWTUtil jwtUtil;

    @Transactional(readOnly = true)
    public String refreshAccessToken(String refreshToken, UUID memberId) {
        System.out.println(refreshToken);
        Token token = tokenRepository.findByRefreshToken(refreshToken);

        if(token.getMemberId() != memberId) {
            throw new IllegalArgumentException();
        }

        String role = jwtUtil.getRole(refreshToken, "refresh");

        return jwtUtil.createAccessToken(memberId, role);
    }
}
