package com.trianglechoke.codesparring.room.dao;

import com.trianglechoke.codesparring.room.entity.RoomMember;
import com.trianglechoke.codesparring.room.entity.RoomMemberKey;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, RoomMemberKey> {
    Optional<RoomMember> findByIdMemberMemberNo(Long memberNo);

    @Modifying
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    void deleteByIdMemberMemberNo(Long memberNo);

    @Query(
            value = "SELECT * FROM room_member WHERE member_no=:memberNo AND host_status=0",
            nativeQuery = true)
    Optional<RoomMember> isRoomMemberHost(@Param("memberNo") Long memberNo);
}
