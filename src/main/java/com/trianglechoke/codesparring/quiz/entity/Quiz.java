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
/* @ToString 무한루프 생길 것 같아서 주석처리 */
@Builder
@Entity
@Table(name = "Quiz")
@DynamicInsert
@SequenceGenerator(
        name = "quiz_no_seq_generator",
        sequenceName = "quiz_no_seq",
        /* initialValue = 1000, 시작 숫자 (방만 1000부터 시작하고 문제는 1부터 시작하니까 1000 -> 1로 수정) */
        initialValue = 1,
        allocationSize = 1)
/* 문제 Entity */
public class Quiz {
    // [PK] 문제 번호
    @Id
    @Column(name = "quiz_no", nullable = false, columnDefinition = "NUMBER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_no_seq_generator")
    private Long quizNo;

    // [FK] 문제를 제작한 회원 번호
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    // 문제 제목
    @Column(name = "quiz_title", nullable = false, columnDefinition = "VARCHAR2(60)")
    private String quizTitle;

    // 문제 내용
    @Column(name = "quiz_content", nullable = false, columnDefinition = "VARCHAR2(30000)")
    private String quizContent;

    // 문제 난이도
    /* @Column(name = "quiz_tier", columnDefinition = "VARCHAR2(8)") 확장성 감안해서 사이즈 확대 ex. grandmaster 등 */
    @Column(name = "quiz_tier", columnDefinition = "VARCHAR2(15)")
    private String quizTier;

    // 문제 검증 여부 (0은 검증ㅇ)
    @Column(name = "quiz_status", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer quizStatus;

    // 테스트케이스 개수
    /* @Column(name = "testcase_cnt", nullable = false, columnDefinition = "NUMBER") default 설정 */
    @Column(name = "testcase_cnt", nullable = false, columnDefinition = "NUMBER default 0")
    private Integer testcaseCnt;

    // 문제가 제출된 횟수
    @Column(name = "quiz_submit_cnt", nullable = false, columnDefinition = "NUMBER default 0")
    private Integer quizSubmitCnt;

    // 문제를 성공한 횟수
    @Column(name = "quiz_success_cnt", nullable = false, columnDefinition = "NUMBER default 0")
    private Integer quizSuccessCnt;

    // 문제 테스트케이스 입력값에 대한 내용
    @Column(name = "quiz_input", columnDefinition = "VARCHAR2(500)")
    private String quizInput;

    // 문제 테스트케이스 출력값에 대한 내용
    @Column(name = "quiz_output", columnDefinition = "VARCHAR2(500)")
    private String quizOutput;

    // 신고받은 목록
    @OneToMany(mappedBy = "quiz")
    List<Report> reportList;
}
