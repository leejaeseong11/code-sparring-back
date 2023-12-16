package com.trianglechoke.codesparring.rankgame.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RankGameDTO {
    private Long rankNo;
    private Long member1No;
    private Long member2No;
    private String member1Name;
    private String member2Name;
    private Date regdate;
    private Integer readyCnt;
    private Long quizNo;
    private Integer gameResult; // 0, 1, 2
}
