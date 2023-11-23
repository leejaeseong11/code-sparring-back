package com.trianglechoke.codesparring.membercode.entity;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

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
@Table(name = "MemberCode")
@DynamicInsert
/* 회원이 제출한 코드 Entity */
public class MemberCode {
    // [FK] 회원 번호
    @Id
    @ManyToOne
    @JoinColumn(name = "member_no")
    @NotNull
    private Member member;

    // [FK] 문제 번호
    @Id
    @ManyToOne
    @JoinColumn(name = "quiz_no")
    @NotNull
    private Quiz quiz;

    // 정답 여부 (1은 정답, 0은 오답)
    @Column(name = "quiz_correct", columnDefinition = "NUMBER(1)")
    @NotNull
    private Integer quizCorrect;
}
