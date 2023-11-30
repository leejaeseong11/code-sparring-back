package com.trianglechoke.codesparring.room.dto;

import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.room.entity.RoomMember;

import jakarta.persistence.*;

import lombok.*;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private Long roomNo;
    private Quiz quiz;
    private String roomPwd;
    private Integer codeShare;
    private String roomTitle;
    private Integer roomStatus;
    private List<RoomMember> roomMemberList;
    private Set<WebSocketSession> sessions = new HashSet<>();
}
