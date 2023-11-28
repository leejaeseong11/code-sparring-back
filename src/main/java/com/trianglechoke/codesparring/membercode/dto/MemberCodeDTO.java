package com.trianglechoke.codesparring.membercode.dto;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import lombok.*;

@Getter
@Setter
// @AllArgsConstructor
// @NoArgsConstructor
@ToString
public class MemberCodeDTO {
    private Member memberNo;
    private Quiz quizNo;
    private Integer quizCorrect;

}
