package org.example.multimodule.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    BAD_REQUEST_PARAMETER(BAD_REQUEST),
    BAD_REQUIRED_PARAMETER(BAD_REQUEST),
    BAD_DATE_PARAMETER(BAD_REQUEST),
    BAD_PERSONNEL_PARAMETER(BAD_REQUEST),

    CHECKIN_EARLIER_THAN_TODAY(BAD_REQUEST),
    CHECKIN_EARLIER_THAN_CHECKOUT(BAD_REQUEST),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED),
    WRONG_PASSWORD(UNAUTHORIZED),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    NOT_FOUND_ROOM(NOT_FOUND),
    NOT_FOUND_PROPERTY(NOT_FOUND),
    NOT_FOUND_IMAGE(NOT_FOUND),
    NOT_FOUND_REVIEW_SUMMARY(NOT_FOUND),
    NOT_FOUND_REVIEW(NOT_FOUND),
    NOT_FOUND_WISH(NOT_FOUND),
    NOT_FOUND_USER(NOT_FOUND),
    NOT_FOUND_ORDER(NOT_FOUND),
    NOT_FOUND_INQUIRY(NOT_FOUND),
    NOT_FOUND_NOTICE(NOT_FOUND),
    NOT_ENOUGH_STOCK(NOT_FOUND),

    /* 406 NOT_ACCEPTABLE : 허가 불가 */
    WRONG_AUTH_NUMBER(NOT_ACCEPTABLE),
    TIMEOUT_AUTH_VERIFY(NOT_ACCEPTABLE),
    DUPLICATED_REQUEST(NOT_ACCEPTABLE),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT),
    ALREADY_EXISTS_USER(CONFLICT),
    ALREADY_EXISTS_NICKNAME(CONFLICT),
    ALREADY_EXISTS_WISH(CONFLICT),
    ALREADY_EXISTS_ORDER(CONFLICT),


    /* 500 CONFLICT : 서버 오류 */
    RUNTIME(INTERNAL_SERVER_ERROR),
    NAVER_SMS_API_ERROR(INTERNAL_SERVER_ERROR),
    ;

    private final HttpStatus httpStatus;
//    private final String detail;
}
