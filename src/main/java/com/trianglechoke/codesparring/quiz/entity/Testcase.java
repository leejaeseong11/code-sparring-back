package com.trianglechoke.codesparring.quiz.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
public class Testcase {
    // [PK] 테스트케이스 번호
    @Id
    @Column(name = "testcase_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "testcase_no_seq_generator")
    private Long testcaseNo;

    // [FK] 문제 번호
    @Column(name = "quiz_no")
    private Long quizNo;

    // 입력값
    @Column(name = "testcase_input", columnDefinition = "VARCHAR2(10000)")
    private String testcaseInput;

    // 출력값
    @Column(name = "testcase_output", columnDefinition = "VARCHAR2(10000)")
    private String testcaseOutput;

    // 출력값 수정 메소드
    public void modifyTc(String input, String output) {
        this.testcaseInput = input;
        this.testcaseOutput = output;
    }
}
