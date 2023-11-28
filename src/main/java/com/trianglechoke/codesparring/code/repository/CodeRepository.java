package com.trianglechoke.codesparring.code.repository;

import com.trianglechoke.codesparring.membercode.entity.MemberCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodeRepository extends JpaRepository<MemberCode, Long> {

    @Modifying
    @Query(
            value =
                    "select tc.testcase_no, tc.testcase_output, ti.testcase_input\n"
                        + "from testcase tc join testcase_input ti on(tc.testcase_no ="
                        + " ti.testcase_no)\n"
                        + "where tc.quiz_no=:quizNo",
            nativeQuery = true)
    public List<Object[]> findByQuizNo(@Param("quizNo") Long quizNo);
}
