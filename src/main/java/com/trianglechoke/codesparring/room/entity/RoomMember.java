package com.trianglechoke.codesparring.room.entity;

import com.trianglechoke.codesparring.member.entity.Member;

import jakarta.persistence.*;

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
@Table(name = "room_member")
@DynamicInsert
/* 방 회원 Entity */
public class RoomMember {
    // [FK] 방 번호
    @Id
    @ManyToOne
    @JoinColumn(name = "room_no", nullable = false)
    private Room roomNo;

    // [FK] 회원 번호
    @Id
    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member memberNo;

    // 방장 여부 (0은 방장)
    @Column(name = "host_status", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer hostStatus;
}
