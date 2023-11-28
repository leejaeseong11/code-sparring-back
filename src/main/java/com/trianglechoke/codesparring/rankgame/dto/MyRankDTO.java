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
}
