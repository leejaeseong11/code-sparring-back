package com.trianglechoke.codesparring.rankgame.entity;

import com.trianglechoke.codesparring.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rank_room")
@DynamicInsert
@SequenceGenerator(
        name = "rank_room_no_seq_generator",
        sequenceName = "rank_room_no_seq",
        initialValue = 1,
        allocationSize = 1)
/* 랭크 게임 방 Entity */
public class RankRoom {
    // [PK] 랭크 번호
    @Id
    @Column(name = "rank_room_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rank_room_no_seq_generator")
    private Long rankRoomNo;

    // [FK] 회원1 번호
    @ManyToOne
    @JoinColumn(name = "member1_no")
    private Member member1;

    // 회원2 번호
    @Column(name = "member2_no")
    private Long member2No;

    // 생성일
    @Column(name = "regdate")
    @ColumnDefault("SYSDATE")
    private Date regdate;

    // 티어
    @Column(name = "tier")
    private String tier;

    // 준비 수
    @Column(name = "ready_cnt")
    @ColumnDefault("0")
    private Integer readyCnt;

    // 문제 번호
    @Column(name = "quiz_no")
    private Long quizNo;

    @Column(name="rank_no")
    private Long rankNo;

    public void addMember2(Long member2No) {
        this.member2No=member2No;
    }

    public void ready() {
        this.readyCnt++;
    }

    public void modifyGameQuiz(Long quizNo) {
        this.quizNo=quizNo;
    }

    public void addRankNo(Long rankNo) {
        this.rankNo=rankNo;
    }
}
