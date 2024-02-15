package com.todayyum.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PasswordModifyRequest {

    private Long memberId;
    private String password;
}
