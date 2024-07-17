package com.todayyum.member.infra.entity;

import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.board.infra.entity.CommentEntity;
import com.todayyum.board.infra.entity.YummyEntity;
import com.todayyum.member.domain.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "members")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10, unique = true)
    private String nickname;

    @Column(length = 30)
    private String introduction;

    @Column
    private String profile;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "fromMember", fetch = FetchType.LAZY)
    private List<FollowEntity> followings;

    @OneToMany(mappedBy = "toMember", fetch = FetchType.LAZY)
    private List<FollowEntity> followers;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<BoardEntity> boards;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<YummyEntity> yummys;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
