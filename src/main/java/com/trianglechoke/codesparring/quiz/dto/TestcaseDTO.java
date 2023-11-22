package com.trianglechoke.codesparring.quiz.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TestcaseDTO {
    private Long testcaseNo;
    private Long quizNo;
    private String testcaseInput;
    private String testcaseOutput;
}
