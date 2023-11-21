package com.trianglechoke.codesparring.quiz.entity;



import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.DynamicInsert;
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

@Getter @NoArgsConstructor @AllArgsConstructor @ToString
@Builder
@Entity
@Table(name="Quiz")
@DynamicInsert
@SequenceGenerator(name = "quiz_seq_generator",
sequenceName = "quiz_seq",
initialValue = 1000, //시작 숫자
allocationSize = 1) //증가하는 숫자
public class Quiz {
  @Id
  @Column(name="quize_no", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
  generator = "quize_seq_generator")
  private Long quizNo;


  //회원번호 FK(Member)
  @ManyToOne
  @JoinColumn(name = "member_no")
  private Member member;

  @Column(name="quiz_title", nullable = false, length = 60)
  private String quizTitle;

  @Column(name="quiz_content", nullable = false, length = 30000)
  private String quizContent;

  @Column(name="quiz_tier", length = 8)
  private String quizTier;

  @Column(name="quiz_status", nullable = false, length = 1)
  private Integer quizStatus;

  @Column(name="testcase_cnt", nullable = false)
  private Integer testcaseCnt;

  @Column(name="quiz_submit_cnt", nullable = false)
  private Integer quizSubmitCnt;

  @Column(name="quiz_success_cnt", nullable = false)
  private Integer quizSuccessCnt;

  @Column(name="quiz_input", length = 500)
  private String quizInput;

  @Column(name="quiz_output", length = 500)
  private String quizOutput;


  @OneToMany(mappedBy = "board")
  List<Report> posts = new ArrayList<>();
}
