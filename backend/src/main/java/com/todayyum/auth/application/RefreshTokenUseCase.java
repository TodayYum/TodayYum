package com.todayyum.auth.application;

import com.todayyum.auth.application.repository.TokenRepository;
import com.todayyum.auth.domain.Token;
import com.todayyum.auth.util.JWTUtil;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
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
        Token token = tokenRepository.findByMemberId(memberId);

        if(!token.getMemberId().equals(memberId)) {
            throw new CustomException(ResponseCode.INVALID_REFRESH_TOKEN);
        }

        String role = jwtUtil.getRole(refreshToken, "refresh");

        return jwtUtil.createAccessToken(memberId, role);
    }
}
