package com.trianglechoke.codesparring.room.entity;

import com.trianglechoke.codesparring.member.entity.Member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class RoomMemberKey implements Serializable {
    // [FK] 방 번호
    @Column(name = "room_no")
    @NotNull
    private Long roomNo;

    // [FK] 회원 번호
    @ManyToOne
    @JoinColumn(name = "member_no", unique = true)
    @NotNull
    private Member member;
}
