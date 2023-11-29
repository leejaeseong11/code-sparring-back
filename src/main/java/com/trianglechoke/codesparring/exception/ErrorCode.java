package com.trianglechoke.codesparring.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    QUIZ_NOT_SAVED(BAD_REQUEST, "문제를 추가할 수 없습니다."),
    TESTCASE_NOT_SAVED(BAD_REQUEST, "테스트케이스를 추가할 수 없습니다."),
    RANK_NOT_SAVED(BAD_REQUEST, "랭크 정보를 추가할 수 없습니다."),
    ALREADY_STARTED_ROOM(BAD_REQUEST, "이미 게임이 시작된 방입니다."),

    /* 403 FORBIDDEN : 접근 권한 제한 */

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    QUIZ_LIST_NOT_FOUND(NOT_FOUND, "문제 목록을 찾을 수 없습니다."),
    QUIZ_NOT_FOUND(NOT_FOUND, "해당 문제를 찾을 수 없습니다."),
    TESTCASE_NOT_FOUND(NOT_FOUND, "테스트케이스를 찾을 수 없습니다."),
    RANK_GAME_NOT_FOUND(NOT_FOUND, "랭크 전적을 찾을 수 없습니다."),
    ROOM_NOT_FOUND(NOT_FOUND, "해당 방 정보를 찾을 수 없습니다."),

    /* 304 NOT_MODIFIED : 클라이언트가 가지고 있는 Resource 가 수정되지 않았음 */
    QUIZ_NOT_MODIFIED(NOT_MODIFIED, "해당 문제를 수정할 수 없습니다."),
    TESTCASE_NOT_MODIFIED(NOT_MODIFIED, "해당 테스트케이스를 수정할 수 없습니다."),
    RANK_GAME_NOT_MODIFIED(NOT_MODIFIED, "랭크게임 결과를 업데이트할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
