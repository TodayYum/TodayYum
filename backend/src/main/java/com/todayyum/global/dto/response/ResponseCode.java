package com.todayyum.global.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

    OK(HttpStatus.OK, "요청이 완료되었습니다."),
    CREATED(HttpStatus.CREATED, "생성이 완료되었습니다."),
    EMPTY_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류로 요청을 처리할 수 없습니다."),

    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    VALID_EMAIL(HttpStatus.OK, "사용 가능한 이메일입니다."),

    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "유효하지 않은 닉네임입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    VALID_NICKNAME(HttpStatus.OK, "사용 가능한 닉네임입니다."),

    MEMBER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "입력한 식별자에 해당하는 사용자가 없습니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "입력한 이메일에 해당하는 사용자가 없습니다."),
    BOARD_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "입력한 식별자에 해당하는 게시글이 없습니다."),
    COMMENT_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "입력한 식별자에 해당하는 댓글이 없습니다."),

    EMPTY_FILE(HttpStatus.BAD_REQUEST, "파일이 존재하지 않습니다."),
    INVALID_FILE(HttpStatus.BAD_REQUEST, "유효하지 않은 파일 형식입니다."),
    S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다."),

    LOGIN_ERROR_CREDENTIALS_INVALID(HttpStatus.UNAUTHORIZED, "로그인 정보가 올바르지 않습니다."),
    REFRESH_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 없습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),

    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패했습니다."),
    EMAIL_SEND_SUCCESS(HttpStatus.OK, "이메일 전송에 성공했습니다."),

    EMAIL_VERIFICATION_FAILED(HttpStatus.BAD_REQUEST, "이메일 검증에 실패했습니다."),

    DUPLICATE_FOLLOW(HttpStatus.CONFLICT, "이미 팔로우 중인 사용자입니다."),
    NOT_FOLLOW(HttpStatus.NOT_FOUND, "팔로우 중이지 않은 사용자입니다."),

    SELF_FOLLOW(HttpStatus.BAD_REQUEST, "본인을 팔로우할 수 없습니다."),
    SELF_UNFOLLOW(HttpStatus.BAD_REQUEST, "본인을 언팔로우할 수 없습니다."),

    INVALID_MEAL_TIME(HttpStatus.BAD_REQUEST, "유효하지 않은 식사 시간입니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "유효하지 않은 카테고리입니다."),

    WRITER_USER_MISMATCH(HttpStatus.BAD_REQUEST, "작성자와 이용자가 일치하지 않습니다."),

    DUPLICATE_YUMMY(HttpStatus.CONFLICT, "이미 야미 중인 게시글입니다."),
    NOT_YUMMY(HttpStatus.NOT_FOUND, "야미 중이지 않은 게시글입니다."),
    ;

    private final HttpStatus status;
    private final String message;

    ResponseCode(HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
