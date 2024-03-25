package com.todayyum.board.domain;

import com.todayyum.board.dto.request.BoardAddRequest;
import com.todayyum.board.infra.entity.BoardEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
public class Board {
    private Long id;
    private UUID memberId;
    private String content;
    private Integer tasteScore;
    private Integer priceScore;
    private Integer moodScore;
    private Float totalScore;
    private Category category;
    private LocalDate ateAt;
    private MealTime mealTime;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Board createBoard(BoardAddRequest boardAddRequest) {
        return Board.builder()
                .memberId(boardAddRequest.getMemberId())
                .content(boardAddRequest.getContent())
                .tasteScore(boardAddRequest.getTasteScore())
                .priceScore(boardAddRequest.getPriceScore())
                .moodScore(boardAddRequest.getMoodScore())
                .totalScore(boardAddRequest.getTotalScore())
                .category(boardAddRequest.getCategory())
                .ateAt(boardAddRequest.getAteAt())
                .mealTime(boardAddRequest.getMealTime())
                .build();
    }

    public static Board createBoard(BoardEntity boardEntity) {
        return Board.builder()
                .id(boardEntity.getId())
                .content(boardEntity.getContent())
                .tasteScore(boardEntity.getTasteScore())
                .priceScore(boardEntity.getPriceScore())
                .moodScore(boardEntity.getMoodScore())
                .totalScore(boardEntity.getTotalScore())
                .category(boardEntity.getCategory())
                .ateAt(boardEntity.getAteAt())
                .mealTime(boardEntity.getMealTime())
                .createdAt(boardEntity.getCreatedAt())
                .modifiedAt(boardEntity.getModifiedAt())
                .build();
    }

    public BoardEntity createEntity() {
        return BoardEntity.builder()
                .id(this.getId())
                .content(this.getContent())
                .tasteScore(this.getTasteScore())
                .priceScore(this.getPriceScore())
                .moodScore(this.getMoodScore())
                .totalScore(this.getTotalScore())
                .category(this.getCategory())
                .ateAt(this.getAteAt())
                .mealTime(this.getMealTime())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }

}
