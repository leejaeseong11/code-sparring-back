package com.trianglechoke.codesparring.rankgame.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MyRankDTO {
    Long rankNo;
    String gameResult;
    Long opposingNo;
    String opposingName;

    String memberName;
    String myTier; // 회원 티어
    Long myPoint; // 회원 현재 포인트
    Long nextPoint; // 다음 랭크가 되기위한 포인트
}
