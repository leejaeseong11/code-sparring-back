package com.trianglechoke.codesparring.member.repository;

import com.trianglechoke.codesparring.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {}
