package com.trianglechoke.codesparring.membercode.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import java.time.LocalDateTime;

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

    private String quizDt;
}
