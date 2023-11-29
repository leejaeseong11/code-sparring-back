package com.trianglechoke.codesparring.code.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class RankDTO {
    private Long memberNo;
    private Long quizNo;
    private Long rankNo;
}
