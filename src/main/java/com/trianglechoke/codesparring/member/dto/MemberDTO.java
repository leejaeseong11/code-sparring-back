package com.trianglechoke.codesparring.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.entity.Role;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import static com.trianglechoke.codesparring.member.entity.Role.USER;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DynamicInsert
public class MemberDTO {
    private Long memberNo;
    private String memberId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // 응답값에 password 포함하지 않기 위해 JsonIgnore 어노테이션을 사용했을 때
    // Deserialize까지 무시하게 되어 유효성 에러가 발생
    // -> 쓰려는 경우(deserialize)에만 접근 허용할 수 있도록 함
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

    public static MemberDTO memberEntityToDTO(Member member) {
        if(member == null) return null;
        return MemberDTO.builder()
                .memberNo(member.getMemberNo())
                .memberId(member.getMemberId())
                .memberPwd(member.getMemberPwd())
                .memberName(member.getMemberName())
                .memberInfo(member.getMemberInfo())
                .memberProfileImg(member.getMemberProfileImg() != null ? member.getMemberProfileImg() : 0)
                .memberLevel(member.getMemberLevel() != null ? member.getMemberLevel() : 1L)
                .memberExp(member.getMemberExp() != null ? member.getMemberExp() : 0)
                .memberTier(member.getMemberTier() != null ? member.getMemberTier() : "BRONZE")
                .tierPoint(member.getTierPoint() != null ? member.getTierPoint() : 0L)
                .winCnt(member.getWinCnt() != null ? member.getWinCnt() : 0L)
                .loseCnt(member.getLoseCnt() != null ? member.getLoseCnt() : 0L)
                .drawCnt(member.getDrawCnt() != null ? member.getDrawCnt() : 0L)
                .memberStatus(member.getMemberStatus() != null ? member.getMemberStatus() : 1)
                .role(member.getRole() != null ? member.getRole() : USER)
                .build();
    }

}



