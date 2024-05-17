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
    private String introduction;

    public MemberListResponse(UUID memberId, String nickname, String profile, String introduction) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profile = profile;
        this.introduction = introduction;
    }
}
