package com.todayyum.auth.domain;

import com.todayyum.auth.infra.entity.TokenEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class Token {
    private String refreshToken;
    private UUID memberId;

    public TokenEntity createEntity() {
        return TokenEntity.builder()
                .refreshToken(this.refreshToken)
                .memberId(this.memberId)
                .build();
    }

    public static Token createToken(TokenEntity tokenEntity) {
        return Token.builder()
                .refreshToken(tokenEntity.getRefreshToken())
                .memberId(tokenEntity.getMemberId())
                .build();
    }
}
