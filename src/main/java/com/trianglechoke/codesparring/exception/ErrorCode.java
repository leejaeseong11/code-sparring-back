package com.trianglechoke.codesparring.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    ALREADY_STARTED_ROOM(BAD_REQUEST, "이미 게임이 시작된 방입니다."),

    /* 403 FORBIDDEN : 접근 권한 제한 */

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    QUIZ_LIST_NOT_FOUND(NOT_FOUND, "문제 목록을 찾을 수 없습니다."),
    QUIZ_NOT_FOUND(NOT_FOUND, "해당 문제를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(NOT_FOUND, "해당 방 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
