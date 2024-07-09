package com.todayyum.auth.application;

import com.todayyum.auth.application.repository.TokenRepository;
import com.todayyum.auth.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RemoveTokenUseCase {

    private final TokenRepository tokenRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public void removeRefreshToken(String refreshToken) {
        UUID memberId = jwtUtil.getMemberId(refreshToken, "refresh");

        tokenRepository.deleteByMemberId(memberId);
    }
}
