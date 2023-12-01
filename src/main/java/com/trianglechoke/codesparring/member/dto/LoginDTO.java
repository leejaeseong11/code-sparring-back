package com.trianglechoke.codesparring.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotNull
    private String memberId;

    @NotNull
    private String memberPwd;
}
