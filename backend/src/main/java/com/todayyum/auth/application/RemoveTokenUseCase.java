package com.todayyum.auth.application;

import com.todayyum.auth.application.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RemoveTokenUseCase {

    private final TokenRepository tokenRepository;

    @Transactional
    public void removeRefreshToken(String refreshToken) {
        tokenRepository.deleteByRefreshToken(refreshToken);
    }
}
