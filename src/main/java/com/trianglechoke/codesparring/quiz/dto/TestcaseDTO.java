package com.trianglechoke.codesparring.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TestcaseDTO {
    private Long testcaseNo;
    private Long quizNo;
    private String testcaseOutput;
    private List<TestcaseInputDTO> testcaseInputDTOList;
}
