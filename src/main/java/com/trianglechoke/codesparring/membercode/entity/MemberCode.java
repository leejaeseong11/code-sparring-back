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
@ToString
@Builder
@Entity
@Table(name = "MemberCode")
@DynamicInsert
public class MemberCode {

    // 회원번호 FK(Member)
    @Id
    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    // 문제번호 FK(Quiz)
    @Id
    @ManyToOne
    @JoinColumn(name = "quiz_no", nullable = false)
    private Quiz quiz;

    @Column(name = "quiz_correct", nullable = false, length = 1)
    private Integer quizCorrect;
}
