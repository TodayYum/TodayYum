package com.todayyum.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberAddRequest {

    private String email;
    private String password;
    private String nickname;

}
