package com.trianglechoke.codesparring.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    FILE_NOT_SAVED(BAD_REQUEST, "파일을 저장할 수 없습니다."),
    QUIZ_NOT_SAVED(BAD_REQUEST, "문제를 추가할 수 없습니다."),
    TESTCASE_NOT_SAVED(BAD_REQUEST, "테스트케이스를 추가할 수 없습니다."),
    RANK_NOT_SAVED(BAD_REQUEST, "랭크 정보를 추가할 수 없습니다."),
    ALREADY_STARTED_ROOM(BAD_REQUEST, "이미 게임이 시작된 방입니다."),
    ALREADY_MEMBER_IN_ROOM(BAD_REQUEST, "이미 입장된 방이 있습니다."),
    WRONG_ROOM_PASSWORD(BAD_REQUEST, "방 암호를 잘못 입력했습니다."),
    SESSION_ERROR(BAD_REQUEST, "세션이 만료되었습니다."),

    MISMATCH_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    /* 401 UNAUTHORIZED : 사용자 인증실패 */
    TOKEN_MISMATCH(UNAUTHORIZED, "회원 정보가 일치하지 않습니다."),
    UNAVAILABLE_REFRESH_TOKEN(UNAUTHORIZED, "유효하지 않은 접근입니다."),

    /* 403 FORBIDDEN : 인가 실패. 특정 리소스에 대한 권한 부족 */
    UNAUTHORIZED_ACCESS(FORBIDDEN, "접근권한이 없습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "존재하지 않는 회원입니다."),
    FILE_NOT_FOUND(NOT_FOUND, "파일을 찾을 수 없습니다."),
    QUIZ_LIST_NOT_FOUND(NOT_FOUND, "문제 목록을 찾을 수 없습니다."),
    QUIZ_NOT_FOUND(NOT_FOUND, "해당 문제를 찾을 수 없습니다."),
    TESTCASE_NOT_FOUND(NOT_FOUND, "테스트케이스를 찾을 수 없습니다."),
    RANK_GAME_NOT_FOUND(NOT_FOUND, "랭크 전적을 찾을 수 없습니다."),
    ROOM_NOT_FOUND(NOT_FOUND, "해당 방 정보를 찾을 수 없습니다."),
    MEMBER_NOT_IN_ROOM(NOT_FOUND, "회원이 참여 중인 방이 없습니다."),

    /* 304 NOT_MODIFIED : 클라이언트가 가지고 있는 Resource 가 수정되지 않았음 */
    QUIZ_NOT_MODIFIED(NOT_MODIFIED, "해당 문제를 수정할 수 없습니다."),
    TESTCASE_NOT_MODIFIED(NOT_MODIFIED, "해당 테스트케이스를 수정할 수 없습니다."),
    RANK_GAME_NOT_MODIFIED(NOT_MODIFIED, "랭크게임 결과를 업데이트할 수 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_MEMBER(CONFLICT, "이미 가입된 회원입니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
