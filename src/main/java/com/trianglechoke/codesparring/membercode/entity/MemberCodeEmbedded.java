package com.trianglechoke.codesparring.membercode.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
// @NoArgsConstructor
// @AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class MemberCodeEmbedded implements Serializable {
    // [FK] 회원 번호

    @ManyToOne
    @JoinColumn(name = "member_no")
    @NotNull
    @JsonIgnore
    private Member member;

    // [FK] 문제 번호

    @ManyToOne
    @JoinColumn(name = "quiz_no")
    @NotNull
    private Quiz quiz;
}
