package com.todayyum.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CodeVerifyRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9._]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$",
            message = "유효하지 않은 이메일입니다.")
    String email;

    @NotBlank(message = "인증 번호를 입력해주세요.")
    @Size(max = 6, min = 6, message = "인증 번호는 6자리입니다.")
    String code;
}
