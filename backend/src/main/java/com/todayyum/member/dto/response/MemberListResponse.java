package com.todayyum.member.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class MemberListResponse {

    private UUID memberId;
    private String nickname;
    private String profile;
    private boolean isFollowing;

    public MemberListResponse(UUID memberId, String nickname, String profile) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profile = profile;
    }
}