package com.trianglechoke.codesparring.membercode.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
@Builder
public class MemberCodeEmbedded implements Serializable {

    // [FK] 회원 번호
    @Column(name = "member_no")
    private Long memberNo;

    // [FK] 문제 번호
    @Column(name = "quiz_no")
    private Long quizNo;
}
