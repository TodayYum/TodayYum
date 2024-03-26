package com.todayyum.board.infra.entity;

import com.todayyum.board.domain.Category;
import com.todayyum.board.domain.MealTime;
import com.todayyum.member.infra.entity.MemberEntity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberEntity member;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(name = "taste_score", nullable = false)
    @Min(0)
    @Max(5)
    private Integer tasteScore;

    @Column(name = "price_score", nullable = false)
    @Min(0)
    @Max(5)
    private Integer priceScore;

    @Column(name = "mood_score", nullable = false)
    @Min(0)
    @Max(5)
    private Integer moodScore;

    @Column(name = "total_score", nullable = false)
    @Min(0)
    @Max(5)
    private Float totalScore;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "ate_at", nullable = false)
    private LocalDate ateAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_time")
    private MealTime mealTime;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<BoardTagEntity> boardTags;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<BoardImageEntity> boardImages;

    @CreatedDate
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    public void changeMember(MemberEntity memberEntity) {
        this.member = memberEntity;
    }
}
