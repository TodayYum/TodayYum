package com.todayyum.member.domain;

import com.todayyum.member.infra.entity.FollowEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
public class Follow {
    private Long id;
    private UUID fromMemberId;
    private UUID toMemberId;
    private LocalDateTime createdAt;

    public static Follow createFollow(UUID fromMemberId, UUID toMemberId) {
        return Follow.builder()
                .fromMemberId(fromMemberId)
                .toMemberId(toMemberId)
                .build();
    }

    public static Follow createFollow(FollowEntity followEntity) {
        return Follow.builder()
                .id(followEntity.getId())
                .fromMemberId(followEntity.getFromMember().getId())
                .toMemberId(followEntity.getToMember().getId())
                .createdAt(followEntity.getCreatedAt())
                .build();
    }

    public void changeId(Long id) {
        this.id = id;
    }
}
