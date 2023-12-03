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
public class MemberRequestDTO {
    private Long memberNo;
    private String memberId;
    private String memberPwd;
    private String memberName;
    private String memberInfo;
    private Integer memberProfileImg;

    public Member toMember(PasswordEncoder passwordEncoder) {
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
