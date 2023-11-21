package com.trianglechoke.codesparring.report.entity;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "Report")
@DynamicInsert
@SequenceGenerator(
        name = "report_no_seq_generator",
        sequenceName = "report_no_seq",
        initialValue = 1,
        allocationSize = 1)
public class Report {
    @Id
    @Column(name = "report_no", nullable = false, columnDefinition = "NUMBER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_no_seq_generator")
    private Long reportNo;

    // 회원번호 FK(Member)
    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    // 문제번호 FK(Quiz)
    @ManyToOne
    @JoinColumn(name = "quiz_no", nullable = false)
    private Quiz quiz;

    @Column(name = "report_content", nullable = false, columnDefinition = "VARCHAR2(100)")
    private String reportContent;

    @Column(name = "report_date", nullable = false, columnDefinition = "DATE")
    @ColumnDefault(value = "SYSDATE")
    private Date reportDate;

    @Column(name = "report_comment", columnDefinition = "VARCHAR2(100)")
    private String reportComment;

    @Column(name = "report_type", columnDefinition = "NUMBER(1)")
    private Integer reportType;
}
