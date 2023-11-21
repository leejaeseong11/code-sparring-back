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

<<<<<<< HEAD
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

=======
>>>>>>> dev
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "Report")
@DynamicInsert
@SequenceGenerator(
        name = "report_seq_generator",
        sequenceName = "report_seq",
        initialValue = 1,
        allocationSize = 1)
public class Report {

    @Id
    @Column(name = "report_no", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_jpa_seq_generator")
    private Long reportNo;

    // 회원번호 FK(Member)
    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    // 문제번호 FK(Quiz)
    @ManyToOne
    @JoinColumn(name = "quiz_no", nullable = false)
    private Quiz quiz;

    @Column(name = "report_content", nullable = false, length = 100)
    private String reportContent;

    @Column(name = "report_date", nullable = false)
    @ColumnDefault(value = "SYSDATE")
    private Date reportDate;

    @Column(name = "report_comment", length = 100)
    private String reportComment;

    @Column(name = "report_type", length = 1)
    private Integer reportType;
}