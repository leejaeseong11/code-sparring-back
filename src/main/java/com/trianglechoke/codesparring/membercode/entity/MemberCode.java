package com.trianglechoke.codesparring.membercode.entity;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
// @ToString
@Builder
@Entity
@Table(name = "MemberCode")
@DynamicInsert
// 회원이 제출한 코드 Entity
public class MemberCode {
    // [FK] 회원 번호
    @Id
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    // [FK] 문제 번호
    @Id
    @ManyToOne
    @JoinColumn(name = "quiz_no")
    private Quiz quizNo;

    // 정답 여부 (1은 정답, 0은 오답)
    @Column(name = "quiz_correct", nullable = false, columnDefinition = "NUMBER(1)")
    private Integer quizCorrect;
}
