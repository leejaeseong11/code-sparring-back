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
        ROOM_OUT, // 강퇴
        RANK_ENTER,
        RANK_MATCHING,
        RANK_QUIT,

        CODE_ENTER,
        CODE_STATUS,
        CODE_QUIT
    }

    /* 메시지 타입 */
    private MessageType type;

    /* 방 채팅 */
    private Long roomNo; // 방 번호
    private String sender; // 메시지 보낸 사람
    private String message; // 메시지

    /* 랭크 매칭 */
    private Long memberNo;
    private String memberTier;

    /* 코드 공유 */
    private Long codeRoomNo; // 코딩 방 번호
    private String codeSender; // 보낸 사람
    private String codeStatus; // 코드 실행/제출 상태
}
