package com.trianglechoke.codesparring.rankgame.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RankRoomDTO {
    private Long roomNo;
    private Long member1No;
    private Long member2No;
    private Date regdate;
    private String tier;
    private Integer readyCnt;
    private Long quizNo;
    private Long rankNo;
}
