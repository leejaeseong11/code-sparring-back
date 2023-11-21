package com.trianglechoke.codesparring.rankgame.entity;

import com.trianglechoke.codesparring.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rank")
@DynamicInsert
@SequenceGenerator(
        name = "rank_no_seq_generator",
        sequenceName = "rank_no_seq",
        initialValue = 1,
        allocationSize = 1)
public class RankGame {
    @Id
    @Column(name = "rank_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rank_no_seq_generator")
    private Long rankNo;

    @ManyToOne
    @JoinColumn(name = "member1_no")
    private Member member1;

    @ManyToOne
    @JoinColumn(name = "member2_no")
    private Member member2;

    @Column(name = "game_result", columnDefinition = "NUMBER(1)")
    private int gameResult;
}
