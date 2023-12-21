package com.trianglechoke.codesparring.member.entity;

import com.trianglechoke.codesparring.membercode.entity.MemberCode;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "member")
@DynamicInsert
@SequenceGenerator(
        name = "member_no_seq_generator",
        sequenceName = "member_no_seq",
        initialValue = 1,
        allocationSize = 1)
/* 회원 Entity */
public class Member {
    // [PK] 회원 번호
    @Id
    @Column(name = "member_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_no_seq_generator")
    private Long memberNo;

    // 회원 아이디
    @Column(name = "member_id", columnDefinition = "VARCHAR2(15)")
    @NotNull
    @NotBlank(message = "ID를 입력하세요!")
    private String memberId;

    // 회원 비밀번호
    @Column(name = "member_pwd", columnDefinition = "VARCHAR2(100)")
    @NotNull
    @NotBlank(message = "비밀번호를 입력하세요!")
    private String memberPwd;

    // 회원 닉네임
    @Column(name = "member_name", columnDefinition = "VARCHAR2(24)")
    @NotNull
    @NotBlank(message = "닉네임을 입력하세요!")
    private String memberName;

    // 회원 자기소개
    @Column(name = "member_info", columnDefinition = "VARCHAR2(1000)")
    private String memberInfo;

    // 회원 프로필 이미지 번호 (0~9 존재)
    @Column(name = "member_profile_img", columnDefinition = "NUMBER(1) default 0")
    private Integer memberProfileImg;

    // 회원 레벨
    @Column(name = "member_level", columnDefinition = "NUMBER default 1")
    private Long memberLevel;

    // 회원 경험치 (100이 되면 레벨이 오르며 0으로 초기화)
    @Column(name = "member_exp", columnDefinition = "NUMBER default 0")
    private Integer memberExp;

    // 회원 랭크 티어
    @Column(name = "member_tier", columnDefinition = "VARCHAR2(15) default 'BRONZE'")
    private String memberTier;

    // 회원 티어 포인트
    @Column(name = "tier_point", columnDefinition = "NUMBER default 0")
    private Long tierPoint;

    // 회원의 랭크 우승 횟수
    @Column(name = "win_cnt", columnDefinition = "NUMBER default 0")
    private Long winCnt;

    // 회원의 랭크 패배 횟수
    @Column(name = "lose_cnt", columnDefinition = "NUMBER default 0")
    private Long loseCnt;

    // 회원의 랭크 무승부 횟수
    @Column(name = "draw_cnt", columnDefinition = "NUMBER default 0")
    private Long drawCnt;

    // 회원의 활성화 상태 (0은 비활성화)
    @Column(name = "member_status", columnDefinition = "NUMBER(1) default 1")
    private Integer memberStatus;

    // 회원의 권한
    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    // 회원의 제출한 코드 목록
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_no")
    private List<MemberCode> memberCodeList;

    // rankGame 이후 point 변경
    public void modifyPoint(Integer point) {
        this.tierPoint += point;
        if(point<=0) this.tierPoint=0L;
    }

    // point 에 따른 tier 변경
    public void modifyTier(String tier) {
        this.memberTier = tier;
    }

    // rankGame 결과에 따른 승률 변경 WIN-winCnt++, LOSE-loseCnt++, DRAW-drawCnt++
    public void modifyCnt(Integer gameResult) {
        if (gameResult == 0) this.drawCnt++;
        else if (gameResult == 1) this.winCnt++;
        else if (gameResult == -1) this.loseCnt++;
    }

    public void modifyMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public void modifyMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void modifyMemberInfo(String memberInfo) {
        this.memberInfo = memberInfo;
    }

    public void modifyMemberProfileImg(Integer memberProfileImg) {
        this.memberProfileImg = memberProfileImg;
    }

    public void removeMember(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }
}
