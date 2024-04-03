package com.todayyum.board.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
public class CommentListResponse {
    private Long id;
    private String nickname;
    private UUID memberId;
    private String content;
    private String profile;
    private LocalDateTime modifiedAt;

    public CommentListResponse(
            Long id, String nickname, UUID memberId, String content, String profile, LocalDateTime modifiedAt) {
        this.id = id;
        this.nickname = nickname;
        this.memberId = memberId;
        this.content = content;
        this.profile = profile;
        this.modifiedAt = modifiedAt;
    }
}
