package com.todayyum.board.domain;

import com.todayyum.board.dto.request.BoardAddRequest;
import com.todayyum.board.dto.request.BoardModifyRequest;
import com.todayyum.board.dto.response.BoardDetailResponse;
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
    private Long yummyCount;
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
                .memberId(boardEntity.getMember().getId())
                .content(boardEntity.getContent())
                .tasteScore(boardEntity.getTasteScore())
                .priceScore(boardEntity.getPriceScore())
                .moodScore(boardEntity.getMoodScore())
                .totalScore(boardEntity.getTotalScore())
                .category(boardEntity.getCategory())
                .ateAt(boardEntity.getAteAt())
                .mealTime(boardEntity.getMealTime())
                .yummyCount(boardEntity.getYummyCount())
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

    public BoardDetailResponse createDetailResponse() {
        return BoardDetailResponse.builder()
                .id(this.id)
                .content(this.content)
                .memberId(this.memberId)
                .category(this.category.name())
                .mealTime(this.mealTime.name())
                .tasteScore(this.tasteScore)
                .priceScore(this.priceScore)
                .moodScore(this.moodScore)
                .totalScore(this.totalScore)
                .ateAt(this.ateAt)
                .yummyCount(this.yummyCount)
                .build();
    }

    public void change(BoardModifyRequest boardModifyRequest) {
        content = boardModifyRequest.getContent();
        tasteScore = boardModifyRequest.getTasteScore();
        priceScore = boardModifyRequest.getPriceScore();
        moodScore = boardModifyRequest.getMoodScore();
        totalScore = boardModifyRequest.getTotalScore();
        category = boardModifyRequest.getCategory();
        ateAt = boardModifyRequest.getAteAt();
        mealTime = boardModifyRequest.getMealTime();
    }

}
