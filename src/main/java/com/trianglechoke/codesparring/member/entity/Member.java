package com.trianglechoke.codesparring.member.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;


import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member")
@DynamicInsert
@SequenceGenerator(
        name = "member_no_seq_generator",
        sequenceName = "member_no_seq",
        initialValue = 1,
        allocationSize = 1)
public class Member {
    @Id
    @Column(name = "member_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_no_seq_generator")
    private Long memberNo;

    @Column(name = "member_id", nullable = false, columnDefinition = "VARCHAR2(15)")
    private String memberId;

    @Column(name = "member_pwd", nullable = false, columnDefinition = "VARCHAR2(15)")
    private String memberPwd;

    @Column(name = "member_name", nullable = false, columnDefinition = "VARCHAR2(20)")
    private String memberName;

    @Column(name = "member_info", columnDefinition = "VARCHAR2(100)")
    private String memberInfo;

    @Column(name = "member_profile_img", nullable = false, columnDefinition = "NUMBER(1) default 0")
    private Integer memberProfileImg;

    @Column(name = "member_level", nullable = false, columnDefinition = "NUMBER default 1")
    private Long memberLevel;

    @Column(name = "member_exp", nullable = false, columnDefinition = "NUMBER default 0")
    private Integer memberExp;

    @Column(
            name = "member_tier",
            nullable = false,
            columnDefinition = "VARCHAR2(15) default 'BRONZE'")
    private String memberTier;

    @Column(name = "tier_point", nullable = false, columnDefinition = "NUMBER default 0")
    private Long tierPoint;

    @Column(name = "win_cnt", nullable = false, columnDefinition = "NUMBER default 0")
    private Long winCnt;

    @Column(name = "lose_cnt", nullable = false, columnDefinition = "NUMBER default 0")
    private Long loseCnt;

    @Column(name = "draw_cnt", nullable = false, columnDefinition = "NUMBER default 0")
    private Long drawCnt;

    @Column(name = "member_status", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer memberStatus;

    @Column(name = "admin_status", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer adminStatus;
}
