package com.todayyum.board.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;

public enum MealTime {
    BREAKFAST,
    LUNCH,
    DINNER,
    ;

    @JsonCreator
    public static MealTime from(String input) {
        for (MealTime mealTime : MealTime.values()) {
            if (mealTime.name().equals(input)) {
                return mealTime;
            }
        }
        throw new CustomException(ResponseCode.INVALID_MEAL_TIME);
    }
}
