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
//    @Size(min = 3,max = 50)
    private String memberId;

    @NotNull
//    @Size(min = 3,max = 100)
    private String memberPwd;
}
