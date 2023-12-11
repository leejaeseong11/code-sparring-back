package com.trianglechoke.codesparring.quiz.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TestcaseInputDTO {
    private Long inputNo;
    private Long testcaseNo;
    private String testcaseInput;
}
