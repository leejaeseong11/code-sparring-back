package com.trianglechoke.codesparring.room.dao;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.room.entity.RoomMember;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Member> {
    //    Optional<RoomMember> findByMemberNo(Long memberNo);
}
