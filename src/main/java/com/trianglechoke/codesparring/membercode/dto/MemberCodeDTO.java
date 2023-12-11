package com.trianglechoke.codesparring.membercode.dto;

import lombok.*;

@Getter
@Setter
// @AllArgsConstructor
// @NoArgsConstructor
@ToString
@Builder
public class MemberCodeDTO {

    private Long memberNo;
    private Long quizNo;
    private Integer quizCorrect;
    private String quizUrl;
}
