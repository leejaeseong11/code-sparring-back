package com.trianglechoke.codesparring.rankgame.dto;

import lombok.*;

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
    private Long quizNo;
    private String rankTier;
    private Integer gameResult; // 0, 1, 2
}
