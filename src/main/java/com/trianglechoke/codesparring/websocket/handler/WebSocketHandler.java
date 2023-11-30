package com.trianglechoke.codesparring.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.service.RoomService;
import com.trianglechoke.codesparring.websocket.domain.Message;
import com.trianglechoke.codesparring.websocket.domain.Message.MessageType;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Arrays;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final RoomService roomService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        String payload = message.getPayload();
        Message readMessage = objectMapper.readValue(payload, Message.class);
        MessageType readMessageType = readMessage.getType();

        // 방에 대한 payload type
        MessageType[] roomMessageTypes =
                new MessageType[] {
                    MessageType.ROOM_ENTER, MessageType.ROOM_TALK, MessageType.ROOM_QUIT,
                };
        if (Arrays.asList(roomMessageTypes).contains(readMessageType)) {
            RoomDTO room = roomService.findRoomByRoomNo(readMessage.getRoomNo());
            Set<WebSocketSession> sessions = room.getSessions();
            if (readMessageType.equals(MessageType.ROOM_ENTER)) {

            } else if (readMessageType.equals(MessageType.ROOM_TALK)) {

            } else if (readMessageType.equals(MessageType.ROOM_QUIT)) {

            }
        }
    }
}
