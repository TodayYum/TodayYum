package com.todayyum.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
public class NicknameModifyRequest {

    private UUID memberId;
    private String nickname;
}
