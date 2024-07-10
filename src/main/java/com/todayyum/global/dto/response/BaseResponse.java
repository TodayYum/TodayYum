package com.todayyum.global.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class BaseResponse {

    private String message;
    private Object result;

    public static ResponseEntity<BaseResponse> createResponseEntity(ResponseCode responseCode, Object result) {
        BaseResponse response = new BaseResponse();
        response.message = responseCode.getMessage();
        response.result = result;
        return new ResponseEntity<>(response, responseCode.getStatus());
    }

    public static ResponseEntity<BaseResponse> createResponseEntity(ResponseCode responseCode) {
        BaseResponse response = new BaseResponse();
        response.message = responseCode.getMessage();
        return new ResponseEntity<>(response, responseCode.getStatus());
    }
}
