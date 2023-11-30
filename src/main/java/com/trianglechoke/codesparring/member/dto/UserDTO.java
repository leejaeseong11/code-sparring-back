package com.trianglechoke.codesparring.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull
    @Size(min = 3,max = 50)
    private String memberId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3,max = 100)
    private String memberPwd;

    @NotNull
    @Size(min = 3,max = 50)
    private String memberName;
}
