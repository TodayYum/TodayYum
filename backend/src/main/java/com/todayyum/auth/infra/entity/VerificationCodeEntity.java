package com.todayyum.auth.infra.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "verificationCode", timeToLive = 60 * 5)
@DynamicUpdate
@DynamicInsert
public class VerificationCodeEntity {
    @Id
    private String email;

    private String code;
}
