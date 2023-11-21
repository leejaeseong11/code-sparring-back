package com.trianglechoke.codesparring.member.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

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

    @Column(name = "member_id", nullable = false, columnDefinition = "VARCHAR(15)")
    private String memberId;

    @Column(name = "member_pwd", nullable = false, columnDefinition = "VARCHAR(15)")
    private String memberPwd;

    @Column(name = "member_name", nullable = false, columnDefinition = "VARCHAR(20)")
    private String memberName;

    @Column(name = "member_info", columnDefinition = "VARCHAR(100)")
    private String memberInfo;

    @Column(name = "member_profile_img", nullable = false, columnDefinition = "NUMBER(1) default 0")
    private int memberProfileImg;

    @Column(name = "member_level", nullable = false, columnDefinition = "default 1")
    private int memberLevel;

    @Column(name = "member_exp", nullable = false, columnDefinition = "default 0")
    private int memberExp;

    @Column(
            name = "member_tier",
            nullable = false,
            columnDefinition = "VARCHAR(15) default 'BRONZE'")
    private String memberTier;

    @Column(name = "tier_point", nullable = false, columnDefinition = "default 0")
    private Long tierPoint;

    @Column(name = "win_cnt", nullable = false, columnDefinition = "default 0")
    private Long winCnt;

    @Column(name = "lose_cnt", nullable = false, columnDefinition = "default 0")
    private Long loseCnt;

    @Column(name = "draw_cnt", nullable = false, columnDefinition = "default 0")
    private Long drawCnt;

    @Column(name = "member_status", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private int memberStatus;

    @Column(name = "admin_status", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private int adminStatus;
}
