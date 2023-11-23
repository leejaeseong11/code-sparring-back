package com.trianglechoke.codesparring.quiz.Repository;

import com.trianglechoke.codesparring.quiz.entity.Testcase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
    @Query(value = "SELECT * FROM TESTCASE WHERE quiz_no=:quizNo", nativeQuery = true)
    public List<Object[]> findAllByQuizNo(Long quizNo);
}
