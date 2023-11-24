package com.trianglechoke.codesparring.member.dto;

import com.trianglechoke.codesparring.member.entity.Role;
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

    private Integer memberProfileImg;
    private Long memberLevel;
    private Integer memberExp;
    private String memberTier;
    private Long tierPoint;
    private Long winCnt;
    private Long loseCnt;
    private Long drawCnt;
    private Integer memberStatus;
    private Role role;


}
