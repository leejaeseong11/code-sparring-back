package com.trianglechoke.codesparring.code.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO {
    private String result;
    private Integer gameResult;
}
