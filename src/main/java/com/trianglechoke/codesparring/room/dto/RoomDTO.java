package com.trianglechoke.codesparring.room.dto;

import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.room.entity.RoomMember;

import jakarta.persistence.*;

import lombok.*;

import java.util.List;

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
}
