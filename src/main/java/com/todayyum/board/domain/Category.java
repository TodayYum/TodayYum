package com.todayyum.board.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;

public enum Category {
    KOREAN_FOOD,
    JAPANESE_FOOD,
    CHINESE_FOOD,
    ASIAN_FOOD,
    WESTERN_FOOD,
    INDIAN_FOOD,
    MEXICAN_FOOD,
    ;

    @JsonCreator
    public static Category from(String input) {
        for (Category category : Category.values()) {
            if (category.name().equals(input)) {
                return category;
            }
        }
        throw new CustomException(ResponseCode.INVALID_CATEGORY);
    }
}
