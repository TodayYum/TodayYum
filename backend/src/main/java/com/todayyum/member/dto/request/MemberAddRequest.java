package com.todayyum.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MemberAddRequest {

    private String email;
    private String password;
    private String nickname;

}
