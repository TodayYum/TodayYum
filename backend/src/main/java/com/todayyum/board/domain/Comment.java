package com.todayyum.board.domain;

import com.todayyum.board.dto.request.CommentAddRequest;
import com.todayyum.board.infra.entity.CommentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
public class Comment {
    private Long id;
    private UUID memberId;
    private Long boardId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Comment createComment(CommentAddRequest commentAddRequest) {
        return Comment.builder()
                .boardId(commentAddRequest.getBoardId())
                .memberId(commentAddRequest.getMemberId())
                .content(commentAddRequest.getContent())
                .build();
    }

    public static Comment createComment(CommentEntity commentEntity) {
        return Comment.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .boardId(commentEntity.getBoard().getId())
                .memberId(commentEntity.getMember().getId())
                .createdAt(commentEntity.getCreatedAt())
                .modifiedAt(commentEntity.getModifiedAt())
                .build();
    }

    public CommentEntity createEntity() {
        return CommentEntity.builder()
                .id(this.id)
                .content(this.content)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    public void changeContent(String content) { this.content = content; }
}
