package com.trianglechoke.codesparring.member.dto;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.entity.Authority;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {
    private String memberId;
    private String memberPwd;
    private String memberName;
    private String memberInfo;
    private String memberPwdCheck;
    private long memberNo;

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
