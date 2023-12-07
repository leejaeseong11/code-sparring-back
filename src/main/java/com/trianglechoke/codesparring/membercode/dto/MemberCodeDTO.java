package com.trianglechoke.codesparring.membercode.dto;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

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
