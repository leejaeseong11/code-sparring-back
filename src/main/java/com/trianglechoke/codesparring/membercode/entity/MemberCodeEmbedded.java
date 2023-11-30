package com.trianglechoke.codesparring.membercode.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

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
