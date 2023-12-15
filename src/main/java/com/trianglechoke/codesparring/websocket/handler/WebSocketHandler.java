package com.trianglechoke.codesparring.websocket.handler;

import static com.trianglechoke.codesparring.exception.ErrorCode.SESSION_ERROR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.room.dto.RoomDTO;
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
    private final Map<Long, Set<WebSocketSession>> roomSessionMap = new HashMap<>();
    private final Map<Long, WebSocketSession> memberSessionMap = new HashMap<>();
    private final Map<Long, String> memberTierMap = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        String payload = message.getPayload();
        Message readMessage = objectMapper.readValue(payload, Message.class);
        MessageType readMessageType = readMessage.getType();

        // room payload type
        MessageType[] roomMessageTypes =
                new MessageType[] {
                    MessageType.ROOM_ENTER, MessageType.ROOM_TALK, MessageType.ROOM_QUIT,
                };

        // rank payload type
        MessageType[] rankMessageTypes =
                new MessageType[] {
                    MessageType.RANK_ENTER, MessageType.RANK_MATCHING, MessageType.RANK_QUIT,
                };

        if (Arrays.asList(roomMessageTypes).contains(readMessageType)) {
            Long roomNo = readMessage.getRoomNo();
            if (!roomSessionMap.containsKey(roomNo)) {
                roomSessionMap.put(roomNo, new HashSet<>());
            }
            Set<WebSocketSession> roomSessions = roomSessionMap.get(roomNo);
            RoomDTO room = roomService.findRoomByRoomNo(roomNo);

            if (readMessageType.equals(MessageType.ROOM_ENTER)) {
                roomSessions.add(session);
                // todo - set user nickname from member token (security util)
                sendToEachSocket(
                        roomSessions, new TextMessage(readMessage.getSender() + "님이 입장했습니다."));
            } else if (readMessageType.equals(MessageType.ROOM_TALK)) {
                sendToEachSocket(
                        roomSessions,
                        new TextMessage(readMessage.getSender() + ": " + readMessage.getMessage()));
            } else if (readMessageType.equals(MessageType.ROOM_QUIT)) {
                roomSessions.remove(session);
                // todo - set user nickname from member token (security util)
                sendToEachSocket(
                        roomSessions, new TextMessage(readMessage.getSender() + "님이 퇴장했습니다."));
            }
        } else if (Arrays.asList(rankMessageTypes).contains(readMessageType)) {
            System.out.println(readMessageType);
            Long memberNo = readMessage.getMemberNo();

            if (readMessageType.equals(MessageType.RANK_ENTER)) {
            } else if (readMessageType.equals(MessageType.RANK_MATCHING)) {
                memberSessionMap.put(memberNo, session);
                memberTierMap.put(memberNo, readMessage.getMemberTier());
                Set<WebSocketSession> memberSessions = new HashSet<>();
                for (Map.Entry<Long, String> elem : memberTierMap.entrySet()) {
                    Long no = elem.getKey();
                    String tier = elem.getValue();
                    System.out.println(memberTierMap.entrySet());
                    if (no.equals(memberNo)) continue;
                    System.out.println(no + tier);
                    if (tier.equals(readMessage.getMemberTier())) {
                        readMessage.setMessage(memberNo + "/" + no);
                        memberSessions.add(memberSessionMap.get(no));
                        memberSessions.add(memberSessionMap.get(memberNo));
                        System.out.println("matching success");
                        sendToEachSocket(
                                memberSessions,
                                new TextMessage(objectMapper.writeValueAsString(readMessage)));
                        break;
                    }
                }
            } else if (readMessageType.equals(MessageType.RANK_QUIT)) {

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
