package com.todayyum.board.domain;

import com.todayyum.board.infra.entity.YummyEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
public class Yummy {
    private Long id;
    private UUID memberId;
    private Long boardId;
    private LocalDateTime createdAt;

    public static Yummy createYummy(UUID memberId, Long boardId) {
        return Yummy.builder()
                .memberId(memberId)
                .boardId(boardId)
                .build();
    }

    public static Yummy createYummy(YummyEntity yummyEntity) {
        return Yummy.builder()
                .id(yummyEntity.getId())
                .memberId(yummyEntity.getMember().getId())
                .boardId(yummyEntity.getBoard().getId())
                .createdAt(yummyEntity.getCreatedAt())
                .build();
    }
}
