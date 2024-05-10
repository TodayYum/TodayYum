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
    private Long boardId;
    private UUID memberId;
    private String nickname;
    private String content;
    private String profile;
    private LocalDateTime modifiedAt;

    public CommentListResponse(Long id, Long boardId, UUID memberId, String nickname,
                               String content, String profile, LocalDateTime modifiedAt) {
        this.id = id;
        this.boardId = boardId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.content = content;
        this.profile = profile;
        this.modifiedAt = modifiedAt;
    }
}
