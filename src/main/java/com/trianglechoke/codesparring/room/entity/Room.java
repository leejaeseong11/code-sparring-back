package com.trianglechoke.codesparring.room.entity;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table(name = "room")
@DynamicInsert
@SequenceGenerator(
        name = "room_no_seq_generator",
        sequenceName = "room_no_seq",
        initialValue = 1000,
        allocationSize = 1)
public class Room {
    @Id
    @Column(name = "room_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_no_seq_generator")
    private Long roomNo;

    @ManyToOne
    @JoinColumn(name = "quiz_no")
    private Quiz quizNo;

    @ManyToOne
    @JoinColumn(name = "win_member")
    private Member winMember;

    @Column(name = "room_pwd", columnDefinition = "NUMBER(4)")
    private Integer roomPwd;

    @Column(name = "code_share", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer codeShare;

    @Column(name = "room_title", nullable = false, columnDefinition = "VARCHAR2(50)")
    private String roomTitle;

    @Column(name = "room_status", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer roomStatus;
}
