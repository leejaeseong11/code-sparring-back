package com.trianglechoke.codesparring.rankgame.entity;

import com.trianglechoke.codesparring.member.entity.Member;

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
@Table(name = "rank_game")
@DynamicInsert
@SequenceGenerator(
        name = "rank_no_seq_generator",
        sequenceName = "rank_no_seq",
        initialValue = 1,
        allocationSize = 1)
/* 랭크 게임 전적 Entity */
public class RankGame {
    // [PK] 랭크 번호
    @Id
    @Column(name = "rank_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rank_no_seq_generator")
    private Long rankNo;

    // [FK] 회원1 번호
    @ManyToOne
    @JoinColumn(name = "member1_no")
    @NotNull
    private Member member1;

    // [FK] 회원2 번호
    @ManyToOne
    @JoinColumn(name = "member2_no")
    @NotNull
    private Member member2;

    // 랭크 게임 결과 (0은 draw, 1은 회원1 win, 2는 회원2 win)
    @Column(name = "game_result", columnDefinition = "NUMBER(1)")
    private Integer gameResult;
}
