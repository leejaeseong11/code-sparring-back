package com.trianglechoke.codesparring.room.dto;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.room.entity.RoomMember;

import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Getter
@Setter
// @AllArgsConstructor
// @NoArgsConstructor
@ToString
public class RoomDTO {

    private Long roomNo;
    private Quiz quizNo;
    private Member winMember;
    private String roomPwd;
    private Integer codeShare;
    private String roomTitle;
    private Integer roomStatus;
    private List<RoomMember> roomMemberList;

}
