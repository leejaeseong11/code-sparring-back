package com.trianglechoke.codesparring.code.dao;

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

    //    @Modifying
    //    @Query(
    //            value =
    //                    "update quiz \n"
    //                            + "set quiz_submit_cnt=quiz_submit_cnt+1, \n"
    //                            + "    quiz_success_cnt=quiz_success_cnt+1\n"
    //                            + "where quiz_no =:100",
    //    nativeQuery = true)
    //    public void modifyQuiz(@Param() String quizNo);
}
