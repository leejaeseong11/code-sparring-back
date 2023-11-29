package com.trianglechoke.codesparring.member.dao;

import com.trianglechoke.codesparring.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = "role") // Eager로 authorities(role) 정보를 조회합니다.
    Optional<Member> findByMemberId(String memberId);
    @Modifying
    @Query("UPDATE Member m SET m.memberPwd = :memberPwd, m.memberName = :memberName, m.memberInfo = :memberInfo WHERE m.memberId = :memberId")
    void modifyMember(@Param("memberId") Long memberId,
                      @Param("memberPwd") String memberPwd,
                      @Param("memberName") String memberName,
                      @Param("memberInfo") String memberInfo);
}
