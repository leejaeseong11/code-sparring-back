package com.trianglechoke.codesparring.member.dto;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DynamicInsert
public class MemberDTO {
    private String memberId;
    private String memberPwd;
    private String memberName;
    private String memberInfo;
}
