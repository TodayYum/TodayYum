package com.todayyum.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PasswordModifyRequest {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d).{8,}$",
            message = "유효하지 않은 비밀번호입니다.")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9._]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$",
            message = "유효하지 않은 이메일입니다.")
    private String email;
}
