package com.trianglechoke.codesparring.quiz.entity;

import jakarta.persistence.*;
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

import org.hibernate.annotations.DynamicInsert;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "Testcase")
@DynamicInsert
@SequenceGenerator(
        name = "testcase_no_seq_generator",
        sequenceName = "testcase_no_seq",
        initialValue = 1,
        allocationSize = 1)
/* 테스트케이스 Entity */
public class Testcase { // test-case 분해 안하는걸로 통일
    // [PK] 테스트케이스 번호
    @Id
    @Column(name = "testcase_no", nullable = false, columnDefinition = "NUMBER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "testcase_no_seq_generator")
    private Long testcaseNo;

    // [FK] 문제 번호
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "quiz_no")
    private Quiz quizNo;

    // 입력값
    @Column(name = "testcase_input", columnDefinition = "VARCHAR2(10000)")
    private String testcaseInput; // 오타수정 (_제거)

    // 출력값
    @Column(name = "testcase_output", columnDefinition = "VARCHAR2(10000)")
    private String testcaseOutput; // 오타수정 (_제거)
}
