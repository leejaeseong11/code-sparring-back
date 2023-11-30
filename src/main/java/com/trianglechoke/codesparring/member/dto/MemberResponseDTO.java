package com.trianglechoke.codesparring.member.dto;

import com.trianglechoke.codesparring.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
    private String memberId;

    public static MemberResponseDTO of(Member member) {
        return new MemberResponseDTO(member.getMemberId());
    }
}
