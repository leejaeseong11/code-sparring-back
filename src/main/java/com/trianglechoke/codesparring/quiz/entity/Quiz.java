package com.trianglechoke.codesparring.quiz.entity;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.report.entity.Report;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "Quiz")
@DynamicInsert
@SequenceGenerator(
        name = "quiz_no_seq_generator",
        sequenceName = "quiz_no_seq",
        initialValue = 1000, // 시작 숫자
        allocationSize = 1) // 증가하는 숫자
public class Quiz {
    @Id
    @Column(name = "quiz_no", nullable = false, columnDefinition = "NUMBER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_no_seq_generator")
    private Long quizNo;

    // 회원번호 FK(Member)
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(name = "quiz_title", nullable = false, columnDefinition = "VARCHAR2(60)")
    private String quizTitle;

    @Column(name = "quiz_content", nullable = false, columnDefinition = "VARCHAR2(30000)")
    private String quizContent;

    @Column(name = "quiz_tier", columnDefinition = "VARCHAR2(8)")
    private String quizTier;

    @Column(name = "quiz_status", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer quizStatus;

    @Column(name = "testcase_cnt", nullable = false, columnDefinition = "NUMBER")
    private Integer testcaseCnt;

    @Column(name = "quiz_submit_cnt", nullable = false, columnDefinition = "NUMBER default 0")
    private Integer quizSubmitCnt;

    @Column(name = "quiz_success_cnt", nullable = false, columnDefinition = "NUMBER default 0")
    private Integer quizSuccessCnt;

    @Column(name = "quiz_input", columnDefinition = "VARCHAR2(500)")
    private String quizInput;

    @Column(name = "quiz_output", columnDefinition = "VARCHAR2(500)")
    private String quizOutput;

    @OneToMany(mappedBy = "quiz")
    List<Report> reports;
}
