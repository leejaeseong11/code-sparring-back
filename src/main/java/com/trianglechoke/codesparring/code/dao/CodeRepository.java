package com.trianglechoke.codesparring.code.dao;

import com.trianglechoke.codesparring.membercode.entity.MemberCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodeRepository extends JpaRepository<MemberCode, Long> {

    //    * quizNo에 해당하는 테스트케이스(testcase_no, testcase_output, testcase_input) 가져오기
    @Modifying
    @Query(
            value =
                    "select testcase_no, testcase_output, testcase_input\n"
                            + "from testcase\n"
                            + "where quiz_no=:quizNo",
            nativeQuery = true)
    public List<Object[]> findByQuizNo(@Param("quizNo") Long quizNo);

    @Query(
            value = "select *\n" + "from member_code\n" + "where member_no=:memberNo",
            nativeQuery = true)
    List<Object[]> findByMemberNo(@Param("memberNo") Long memberNo);

    @Query(
            value =
                    "select *\n"
                            + "from member_code\n"
                            + "where member_no=:memberNo and quiz_no=:quizNo",
            nativeQuery = true)
    MemberCode findByQuizUrl(@Param("memberNo") Long memberNo, @Param("quizNo") Long quizNo);
}
