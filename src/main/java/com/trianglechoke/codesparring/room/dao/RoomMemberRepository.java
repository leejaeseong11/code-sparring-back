package com.trianglechoke.codesparring.room.dao;

import com.trianglechoke.codesparring.room.entity.RoomMember;
import com.trianglechoke.codesparring.room.entity.RoomMemberKey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMemberRepository extends JpaRepository<RoomMember, RoomMemberKey> {
//    @Query(value = "")
//    public Long checkMemberInRoom();
}
