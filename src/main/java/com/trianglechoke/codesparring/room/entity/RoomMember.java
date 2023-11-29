package com.trianglechoke.codesparring.room.entity;

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
@Table(name = "room_member")
@DynamicInsert
/* 방 회원 Entity */
public class RoomMember {
    @EmbeddedId private RoomMemberKey id = new RoomMemberKey();

    // 방장 여부 (0은 방장)
    @Column(name = "host_status", columnDefinition = "NUMBER(1) default 1")
    @NotNull
    private Integer hostStatus;
}
