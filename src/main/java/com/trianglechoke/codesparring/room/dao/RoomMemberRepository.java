package com.trianglechoke.codesparring.room.dao;

import com.trianglechoke.codesparring.room.entity.RoomMember;
import com.trianglechoke.codesparring.room.entity.RoomMemberKey;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, RoomMemberKey> {
    public Optional<RoomMember> findByMemberNo(Long memberNo);
}
