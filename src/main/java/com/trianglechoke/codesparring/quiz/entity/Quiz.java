package com.trianglechoke.codesparring.quiz.entity;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.dto.QuizDTO;
import com.trianglechoke.codesparring.report.entity.Report;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Quiz")
@DynamicInsert
@SequenceGenerator(
        name = "quiz_no_seq_generator",
        sequenceName = "quiz_no_seq",
        initialValue = 1,
        allocationSize = 1)
/* 문제 Entity */
public class Quiz {
    // [PK] 문제 번호
    @Id
    @Column(name = "quiz_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_no_seq_generator")
    @NotNull
    private Long quizNo;

    // [FK] 문제를 제작한 회원 번호
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    // 문제 제목
    @Column(name = "quiz_title", columnDefinition = "VARCHAR2(60)")
    @NotNull
    private String quizTitle;

    // 문제 내용
    @Column(name = "quiz_content", columnDefinition = "VARCHAR2(30000)")
    @NotNull
    private String quizContent;

    // 문제 난이도
    @Column(name = "quiz_tier", columnDefinition = "VARCHAR2(15) default 'UNRANKED'")
    @NotNull
    private String quizTier;

    // 문제가 제출된 횟수
    @Column(name = "quiz_submit_cnt", columnDefinition = "NUMBER default 0")
    @NotNull
    private Integer quizSubmitCnt;

    // 문제를 성공한 횟수
    @Column(name = "quiz_success_cnt", columnDefinition = "NUMBER default 0")
    @NotNull
    private Integer quizSuccessCnt;

    // 문제 테스트케이스 입력값에 대한 내용
    @Column(name = "quiz_input", columnDefinition = "VARCHAR2(500)")
    private String quizInput;

    // 문제 테스트케이스 출력값에 대한 내용
    @Column(name = "quiz_output", columnDefinition = "VARCHAR2(500)")
    private String quizOutput;

    // 출력값(리턴) 타입
    @Column(name = "output_type", columnDefinition = "VARCHAR2(20)")
    @NotNull
    private String outputType;

    // 신고받은 목록
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "quiz_no")
    private List<Report> reportList;

    // 테스트케이스 목록
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "quiz_no")
    private List<Testcase> testcaseList;

    // 문제 수정 메소드
    public void modifyQuiz(QuizDTO quizDTO) {
        this.quizTitle = quizDTO.getQuizTitle();
        this.quizContent = quizDTO.getQuizContent();
        this.quizInput = quizDTO.getQuizInput();
        this.quizOutput = quizDTO.getQuizOutput();
        this.outputType = quizDTO.getOutputType();
    }

    public void modifyQuizSubmit(QuizDTO quizDTO, boolean correct) {
        this.quizSubmitCnt++;
        if (correct) this.quizSuccessCnt++;
    }

    public void modifyQuizTier(String tier) {
        this.quizTier = tier;
    }
}
