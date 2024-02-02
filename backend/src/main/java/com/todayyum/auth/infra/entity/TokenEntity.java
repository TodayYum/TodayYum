package com.todayyum.auth.infra.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 14)
public class TokenEntity implements Serializable {

    @Id
    private String refreshToken;

    private Long memberId;
}
