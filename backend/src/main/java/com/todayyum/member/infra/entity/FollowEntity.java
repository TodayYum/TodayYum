package com.todayyum.member.infra.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberEntity fromMember;

    @ManyToOne
    @JoinColumn(name = "to_member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberEntity toMember;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
