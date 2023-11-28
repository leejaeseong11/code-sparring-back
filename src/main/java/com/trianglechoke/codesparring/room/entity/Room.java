package com.trianglechoke.codesparring.room.entity;

import com.trianglechoke.codesparring.quiz.entity.Quiz;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;

import java.util.List;

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
    @NotNull
    private Quiz quiz;

    // 방 비밀번호 (null은 공개방)
    @Column(name = "room_pwd", columnDefinition = "VARCHAR2(4)")
    private String roomPwd;

    // 코드 공유 여부 (0은 코드 비공개)
    @Column(name = "code_share", columnDefinition = "NUMBER(1) default 1")
    @NotNull
    private Integer codeShare;

    // 방 제목
    @Column(name = "room_title", columnDefinition = "VARCHAR2(50)")
    @NotNull
    private String roomTitle;

    // 방 상태 (1은 대기방, 0은 게임방)
    @Column(name = "room_status", columnDefinition = "NUMBER(1) default 1")
    @NotNull
    private Integer roomStatus;

    // 방 멤버 목록
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "room_no")
    private List<RoomMember> roomMemberList;
}
