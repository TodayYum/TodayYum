package com.todayyum.auth.infra.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 14)
@DynamicUpdate
@DynamicInsert
public class TokenEntity implements Serializable {

    @Id
    private UUID memberId;

    private String refreshToken;
}
