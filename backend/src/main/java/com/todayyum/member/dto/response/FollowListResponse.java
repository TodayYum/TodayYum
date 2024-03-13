package com.todayyum.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
public class FollowListResponse {

    private UUID memberId;
    private String nickname;
    private String profile;

    public FollowListResponse(UUID memberId, String nickname, String profile) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profile = profile;
    }
}
