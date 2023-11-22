package com.trianglechoke.codesparring.room.entity;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

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
@Table(name = "room")
@DynamicInsert
@SequenceGenerator(
        name = "room_no_seq_generator",
        sequenceName = "room_no_seq",
        initialValue = 1000,
        allocationSize = 1)
/* 일반 게임 방 Entity */
public class Room {
    // [PK] 방 번호
    @Id
    @Column(name = "room_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_no_seq_generator")
    private Long roomNo;

    // [FK] 문제 번호
    @ManyToOne
    @JoinColumn(name = "quiz_no")
    private Quiz quizNo;

    // [FK] 우승한 회원 번호
    @ManyToOne
    @JoinColumn(name = "win_member")
    private Member winMember;

    // 방 비밀번호 (null은 공개방)
    @Column(name = "room_pwd", columnDefinition = "NUMBER(4)")
    private Integer roomPwd;

    // 코드 공유 여부 (0은 코드 비공개)
    @Column(name = "code_share", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer codeShare;

    // 방 제목
    @Column(name = "room_title", nullable = false, columnDefinition = "VARCHAR2(50)")
    private String roomTitle;

    // 방 상태 (1은 대기방, 0은 게임방)
    @Column(name = "room_status", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer roomStatus;
}
