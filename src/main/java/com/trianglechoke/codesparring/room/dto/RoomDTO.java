package com.trianglechoke.codesparring.room.dto;

import com.trianglechoke.codesparring.member.entity.Member;

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
    private Long quizNo;
    private Member winMember;
    private String roomPwd;
    private Integer codeShare;
    private String roomTitle;
    private Integer roomStatus;
    private List<Long> roommemberNoList;
}
