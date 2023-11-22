package com.trianglechoke.codesparring.quiz.Repository;

import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.entity.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
//    @Query(
//            value =
//                    "INSERT INTO TESTCASE (testcase_no, quiz_no, testcase_input, testcase_output)\n"
//                            + "VALUES (testcase_no_seq.NEXTVAL, :quizNo, :input, :output)",
//            nativeQuery = true)
//    public void insertTestcase(Long quizNo, String input, String output);

    // @Query(value = "") // query 짜기
    // public List<Object[]> findAllByQuizNo(Long quizNo);
}
