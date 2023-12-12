package com.trianglechoke.codesparring.room.dto;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
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
    private String quizTitle;
    private String quizContent;
    private String memberName;
    private String roomPwd;
    private Integer codeShare;
    private String roomTitle;
    private Integer roomStatus;
    private LocalDateTime roomDt;
    private List<RoomMemberDTO> roomMemberList;
}
