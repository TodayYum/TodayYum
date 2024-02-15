package com.todayyum.global.exception;

import com.todayyum.global.dto.response.ResponseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private ResponseCode responseCode;

    public CustomException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

}