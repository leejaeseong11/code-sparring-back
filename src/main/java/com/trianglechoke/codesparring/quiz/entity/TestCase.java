package com.trianglechoke.codesparring.quiz.entity;

import org.hibernate.annotations.DynamicInsert;
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

@Getter @NoArgsConstructor @AllArgsConstructor @ToString
@Builder
@Entity
@Table(name="TestCase")
@DynamicInsert
@SequenceGenerator(name = "testcase_seq_generator",
                    sequenceName = "testcase_seq",
                    initialValue = 1,
                    allocationSize = 1)
public class TestCase {

  @Id
  @Column(name="testcase_no", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
  generator ="testcase_seq_generator")
  private Long testcaseNo;
  
  //문제번호 FK(Quiz)
  @ManyToOne
  @JoinColumn(name = "quiz_no", nullable = false)
  private Quiz quiz;
  
  @Column(name="testcase_input", length = 10000)
  private String testcase_Input;
  
  @Column(name="testcase_output", length = 10000)
  private String testcase_Output;
}
