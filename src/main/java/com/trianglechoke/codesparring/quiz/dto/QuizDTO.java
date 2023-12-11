package com.trianglechoke.codesparring.quiz.dto;

import com.trianglechoke.codesparring.report.dto.ReportDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class QuizDTO {
    private Long quizNo;
    private Long memberNo;
    private String memberName;
    private String quizTitle;
    private String quizContent;
    private String quizTier;
    private Integer quizSubmitCnt;
    private Integer quizSuccessCnt;
    private String quizInput;
    private String quizOutput;
    private List<ReportDTO> reportDTOList;
    private List<TestcaseDTO> testcaseDTOList;
    private Long quizCnt;
    private String quizCorrectPercent;
}
