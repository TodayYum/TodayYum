package com.todayyum.auth.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CodeVerifyRequest {
    String email;
    String code;
}
