package com.trianglechoke.codesparring.report.entity;

import com.trianglechoke.codesparring.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Report")
@DynamicInsert
@SequenceGenerator(
        name = "report_no_seq_generator",
        sequenceName = "report_no_seq",
        initialValue = 1,
        allocationSize = 1)
/* 신고 Entity */
public class Report {
    // [PK] 신고 번호
    @Id
    @Column(name = "report_no", columnDefinition = "NUMBER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_no_seq_generator")
    @NotNull
    private Long reportNo;

    // [FK] 회원 번호
    @ManyToOne
    @JoinColumn(name = "member_no")
    @NotNull
    private Member member;

    // [FK] 문제 번호
    @Column(name = "quiz_no")
    @NotNull
    private Long quizNo;

    // 신고 내용
    @Column(name = "report_content", columnDefinition = "VARCHAR2(100)")
    @NotNull
    private String reportContent;

    // 신고된 날짜
    @Column(name = "report_date", columnDefinition = "DATE")
    @ColumnDefault(value = "SYSDATE")
    @NotNull
    private Date reportDate;

    // 신고 코멘트
    @Column(name = "report_comment", columnDefinition = "VARCHAR2(100)")
    private String reportComment;

    // 신고 종류 (아직 종류 어떻게 나눌지 미정)
    @Column(name = "report_type", columnDefinition = "NUMBER(1)")
    @NotNull
    private Integer reportType;
}
