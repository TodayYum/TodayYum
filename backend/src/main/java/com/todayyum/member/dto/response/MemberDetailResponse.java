package com.todayyum.member.dto.response;

import com.todayyum.member.domain.Member;
import com.todayyum.member.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
public class MemberDetailResponse {

    private UUID memberId;
    private String email;
    private String nickname;
    private String introduction;
    private String profile;
    private Long followerCount;
    private Long followingCount;
    private Role role;

    public static MemberDetailResponse createResponse(Member member) {
        return MemberDetailResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .introduction(member.getIntroduction())
                .profile(member.getProfile())
                .role(member.getRole())
                .build();
    }

}
