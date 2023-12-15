package com.trianglechoke.codesparring.code.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder

// 코드 실행, 채점에 필요한 테스트케이스 정보만 가져오는 DTO
public class CodeTestcaseDTO {
    private Long testcaseNo;
    private String testcaseInput;
    private String testcaseOutput;
}
