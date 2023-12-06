package com.trianglechoke.codesparring.membercode.entity;

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
    // [FK] 문제 번호
    @EmbeddedId private MemberCodeEmbedded id;

    // 정답 여부 (1은 정답, 0은 오답)
    @Column(name = "quiz_correct", columnDefinition = "NUMBER(1)")
    @NotNull
    private Integer quizCorrect;


    @Column(name = "quiz_url", columnDefinition = "VARCHAR2(500)")
    @NotNull
    private String quizUrl;
}
