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
public class RoomMember {
    @Id
    @ManyToOne
    @JoinColumn(name = "room_no", nullable = false)
    private Room roomNo;

    @Id
    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member memberNo;

    @Column(name = "host_status", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer hostStatus;
}
