package com.trianglechoke.codesparring.membercode.dao;

import com.trianglechoke.codesparring.membercode.entity.MemberCode;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCodeRepository extends JpaRepository<MemberCode, String> {}
