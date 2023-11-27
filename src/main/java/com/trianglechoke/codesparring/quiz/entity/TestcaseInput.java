package com.trianglechoke.codesparring.quiz.entity;

import com.trianglechoke.codesparring.quiz.dto.TestcaseInputDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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
@Table(name = "TestcaseInput")
@DynamicInsert
@SequenceGenerator(
        name = "input_no_seq_generator",
        sequenceName = "input_no_seq",
        initialValue = 1,
        allocationSize = 1)
/* 입력값 Entity */
public class TestcaseInput {
    // [PK] 입력값 번호
    @Id
    @Column(name = "input_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "input_no_seq_generator")
    private Long inputNo;

    // [FK] 테스트케이스 번호
    @Column(name = "testcase_no")
    @NotNull
    private Long testcaseNo;

    // 매개변수
    @Column(name = "input_var", columnDefinition = "VARCHAR2(100)")
    @NotNull
    private String inputVar;

    // 입력값
    @Column(name = "testcase_input", columnDefinition = "VARCHAR2(10000)")
    private String testcaseInput;

    public void modifyInput(TestcaseInputDTO testcaseInput) {
        this.inputVar=testcaseInput.getInputVar();
        this.testcaseInput=testcaseInput.getTestcaseInput();
    }
}
