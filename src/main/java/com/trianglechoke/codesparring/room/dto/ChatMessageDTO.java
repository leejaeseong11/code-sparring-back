package com.trianglechoke.codesparring.room.dto;

public class ChatMessageDTO {
    /* 메시지 타입 : 입장, 채팅, 나감 */
    public enum MessageType {
        ENTER,
        TALK,
        QUIT
    }

    /* 메시지 타입 */
    private MessageType type;
    /* 방 번호 */
    private String roomId;
    /* 메시지 보낸사람 */
    private String sender;
    /* 메시지 */
    private String message;
}
