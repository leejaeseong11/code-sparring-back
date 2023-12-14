package com.trianglechoke.codesparring.websocket.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Message {
    public enum MessageType {
        ROOM_ENTER, // 방 입장
        ROOM_TALK, // 방 채팅
        ROOM_QUIT, // 방 퇴장
        RANK_MATCHING // 랭크 매칭
    }

    /* 메시지 타입 */
    private MessageType type;

    /* 방 채팅 */
    private Long roomNo; // 방 번호
    private String sender; // 메시지 보낸 사람
    private String message; // 메시지

    /* 랭크 매칭 */

    /* 코드 공유 */
    private Long codeRoomNo; // 방 번호
    private String codeSender; // 코드 보낸 사람
    private String code; // 코드
}
