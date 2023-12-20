package com.trianglechoke.codesparring.websocket.handler;

import static com.trianglechoke.codesparring.exception.ErrorCode.SESSION_ERROR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.service.RankGameService;
import com.trianglechoke.codesparring.room.service.RoomService;
import com.trianglechoke.codesparring.websocket.domain.Message;
import com.trianglechoke.codesparring.websocket.domain.Message.MessageType;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final RoomService roomService;
    private final RankGameService rankGameService;
    private final Map<Long, Set<WebSocketSession>> roomSessionMap = new HashMap<>();
    private final Map<Long, WebSocketSession> memberSessionMap = new HashMap<>();
    private final Map<Long, String> memberTierMap = new HashMap<>();

    private final Map<Long, Set<WebSocketSession>> codeSessionMap = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        String payload = message.getPayload();
        Message readMessage = objectMapper.readValue(payload, Message.class);
        MessageType readMessageType = readMessage.getType();
        // room payload type
        MessageType[] roomMessageTypes =
                new MessageType[] {
                    MessageType.ROOM_ENTER,
                    MessageType.ROOM_TALK,
                    MessageType.ROOM_QUIT,
                    MessageType.ROOM_OUT
                };

        MessageType[] codeMessageTypes =
                new MessageType[] {
                    MessageType.CODE_ENTER, MessageType.CODE_STATUS, MessageType.CODE_QUIT,
                };

        if (Arrays.asList(roomMessageTypes).contains(readMessageType)) {
            Long roomNo = readMessage.getRoomNo();
            if (!roomSessionMap.containsKey(roomNo)) {
                roomSessionMap.put(roomNo, new HashSet<>());
            }
            Set<WebSocketSession> roomSessions = roomSessionMap.get(roomNo);

            if (readMessageType.equals(MessageType.ROOM_ENTER)) {
                roomSessions.add(session);
                sendToEachSocket(
                        roomSessions, new TextMessage(readMessage.getSender() + "님이 입장했습니다."));
            } else if (readMessageType.equals(MessageType.ROOM_TALK)) {
                sendToEachSocket(
                        roomSessions,
                        new TextMessage(readMessage.getSender() + ": " + readMessage.getMessage()));
            } else if (readMessageType.equals(MessageType.ROOM_QUIT)) {
                roomSessions.remove(session);
                sendToEachSocket(
                        roomSessions, new TextMessage(readMessage.getSender() + "님이 퇴장했습니다."));
            } else if (readMessageType.equals(MessageType.ROOM_OUT)) {
                sendToEachSocket(
                        roomSessions, new TextMessage(readMessage.getSender() + "님이 강제 퇴장되었습니다."));
            }
        } else if (Arrays.asList(codeMessageTypes).contains(readMessageType)) {
            Long codeRoomNo = readMessage.getCodeRoomNo();
            if (!codeSessionMap.containsKey(codeRoomNo)) {
                codeSessionMap.put(codeRoomNo, new HashSet<>());
            }
            Set<WebSocketSession> codeSessions = codeSessionMap.get(codeRoomNo);
            RankGameDTO rank = rankGameService.findByRankNo(codeRoomNo);

            if (readMessageType.equals(MessageType.CODE_ENTER)) {
                codeSessions.add(session);
                sendToEachSocket(
                        codeSessions, new TextMessage(readMessage.getCodeSender() + "님이 입장했습니다."));
            } else if (readMessageType.equals(MessageType.CODE_STATUS)) {
                sendToEachSocket(
                        codeSessions,
                        new TextMessage(
                                readMessage.getCodeSender() + ": " + readMessage.getCodeStatus()));
            } else if (readMessageType.equals(MessageType.CODE_QUIT)) {
                codeSessions.remove(session);
                sendToEachSocket(
                        codeSessions, new TextMessage(readMessage.getSender() + "님이 퇴장했습니다."));
            }
        }
    }

    private void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message) {
        sessions.parallelStream()
                .forEach(
                        session -> {
                            try {
                                session.sendMessage(message);
                            } catch (IOException e) {
                                throw new MyException(SESSION_ERROR);
                            }
                        });
    }
}
