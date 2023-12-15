package com.trianglechoke.codesparring.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReportDTO {
    private Long reportNo;
    private String memberName;
    private Long quizNo;
    private String reportContent;

    @JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
    private Date reportDate;

    private String reportComment;
    private Integer reportType;
}
