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
@Table(name = "TestCase")
@DynamicInsert
@SequenceGenerator(
        name = "testcase_no_seq_generator",
        sequenceName = "testcase_no_seq",
        initialValue = 1,
        allocationSize = 1)
public class TestCase {

    @Id
    @Column(name = "testcase_no", nullable = false, columnDefinition = "NUMBER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "testcase_no_seq_generator")
    private Long testcaseNo;

    // 문제번호 FK(Quiz)
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "quiz_no")
    private Quiz quiz;

    @Column(name = "testcase_input", columnDefinition = "VARCHAR2(10000)")
    private String testcase_Input;

    @Column(name = "testcase_output", columnDefinition = "VARCHAR2(10000)")
    private String testcase_Output;
}
