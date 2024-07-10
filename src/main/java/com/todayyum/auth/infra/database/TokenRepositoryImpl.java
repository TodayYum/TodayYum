package com.todayyum.auth.infra.database;

import com.todayyum.auth.application.repository.TokenRepository;
import com.todayyum.auth.domain.Token;
import com.todayyum.auth.infra.entity.TokenEntity;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RequiredArgsConstructor
@Repository
@Slf4j
public class TokenRepositoryImpl implements TokenRepository {

    private final RedisTokenRepository redisTokenRepository;

    @Override
    public void save(Token token) {
        TokenEntity tokenEntity = token.createEntity();
        redisTokenRepository.save(tokenEntity);
    }

    @Override
    public Token findByMemberId(UUID memberId) {
        TokenEntity tokenEntity = redisTokenRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ResponseCode.INVALID_REFRESH_TOKEN));

        return Token.createToken(tokenEntity);
    }

    @Override
    public void deleteByMemberId(UUID memberId) {
        redisTokenRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ResponseCode.INVALID_REFRESH_TOKEN));

        redisTokenRepository.deleteById(memberId);
    }
}
