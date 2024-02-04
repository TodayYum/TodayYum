package com.todayyum.member.dto.response;

import com.todayyum.member.domain.Member;
import com.todayyum.member.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MemberDetailResponse {

    private Long memberId;
    private String email;
    private String nickname;
    private String comment;
    private String profile;
    private Role role;

    public static MemberDetailResponse createResponse(Member member) {
        return MemberDetailResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .comment(member.getComment())
                .profile(member.getProfile())
                .role(member.getRole())
                .build();
    }

}
