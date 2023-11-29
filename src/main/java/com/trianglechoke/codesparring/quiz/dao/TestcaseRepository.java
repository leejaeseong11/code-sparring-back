package com.trianglechoke.codesparring.quiz.dao;

import com.trianglechoke.codesparring.quiz.entity.Testcase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {}
