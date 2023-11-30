package com.trianglechoke.codesparring.member.dto;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {
    private String memberId;
    private String memberPwd;
    private String memberName;
    private String memberInfo;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
            .memberId(memberId)
            .memberPwd(passwordEncoder.encode(memberPwd))
            .memberName(memberName)
            .memberInfo(memberInfo)
            .authority(Authority.ROLE_USER)
            .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberId, memberPwd);
    }
}
