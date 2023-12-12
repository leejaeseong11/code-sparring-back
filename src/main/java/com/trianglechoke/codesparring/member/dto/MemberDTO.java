package com.trianglechoke.codesparring.member.dto;

import com.trianglechoke.codesparring.member.entity.Authority;
import com.trianglechoke.codesparring.member.entity.Member;

import lombok.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long memberNo;
    private String memberId;
    private String memberPwd;
    private String memberName;
    private String memberInfo;
    private Integer memberProfileImg;
    private Long memberLevel;
    private Integer memberExp;
    private String memberTier;
    private Long tierPoint;
    private Long loseCnt;
    private Long drawCnt;
    private Long winCnt;
    private Integer rank;

    public Member toMember(PasswordEncoder passwordEncoder) {
        if (memberPwd == null) {
            memberPwd = ""; // 또는 기본값으로 초기화
        }
        return Member.builder()
                .memberId(memberId)
                .memberPwd(passwordEncoder.encode(memberPwd))
                .memberName(memberName)
                .memberInfo(memberInfo)
                .memberProfileImg(memberProfileImg)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberId, memberPwd);
    }
}
