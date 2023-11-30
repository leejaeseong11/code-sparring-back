package com.trianglechoke.codesparring.room.dao;

import com.trianglechoke.codesparring.room.entity.RoomMember;
import com.trianglechoke.codesparring.room.entity.RoomMemberKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, RoomMemberKey> {
    Optional<RoomMember> findByIdMemberMemberNo(Long memberNo);

    @Modifying
    void deleteByIdMemberMemberNo(Long memberNo);
}
