package com.todayyum.auth.domain;

import com.todayyum.auth.dto.request.CodeVerifyRequest;
import com.todayyum.auth.infra.entity.VerificationCodeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class VerificationCode {
    private String email;
    private String code;

    public VerificationCodeEntity createEntity() {
        return VerificationCodeEntity.builder()
                .email(this.email)
                .code(this.code)
                .build();
    }

    public static VerificationCode createVerificationCode(VerificationCodeEntity verificationCodeEntity) {
        return VerificationCode.builder()
                .email(verificationCodeEntity.getEmail())
                .code(verificationCodeEntity.getCode())
                .build();
    }

    public static VerificationCode createVerificationCode(CodeVerifyRequest codeVerifyRequest) {
        return VerificationCode.builder()
                .email(codeVerifyRequest.getEmail())
                .code(codeVerifyRequest.getCode())
                .build();
    }

    public void changeCode(String code) {
        this.code = code;
    }
}
