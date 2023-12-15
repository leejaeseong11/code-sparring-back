package com.trianglechoke.codesparring.quiz.dao;

import com.trianglechoke.codesparring.quiz.entity.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    /* 전체 목록 조회 : default = 제출 횟수 많은 순 */
    @Query(
            value =
                    "SELECT * FROM (\n"
                            + "\tSELECT rownum rn, q.*\n"
                            + "\tFROM (\n"
                            + "\t\tSELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt,"
                            + " quiz_tier\n"
                            + "\t\tFROM quiz ORDER BY quiz_submit_cnt desc\n"
                            + "\t) q\n"
                            + ") pg WHERE rn BETWEEN :start AND :end",
            nativeQuery = true)
    public List<Object[]> findQuizList(@Param("start") Integer start, @Param("end") Integer end);

    /* 전체 목록 조회 : 정답률 높은 순 */
    @Query(
            value =
                    "SELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt,"
                            + " quiz_tier\n"
                            + "\t\tFROM quiz WHERE quiz_submit_cnt<>0\n"
                            + "ORDER BY CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END",
            nativeQuery = true)
    public List<Object[]> findOrderByCorrect();

    /* 전체 목록 조회 : 정답률 낮은 순 */
    @Query(
            value =
                    "SELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt,"
                            + " quiz_tier\n"
                            + "\t\tFROM quiz WHERE quiz_submit_cnt<>0\n"
                            + "ORDER BY CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END desc",
            nativeQuery = true)
    public List<Object[]> findOrderByCorrectDesc();

    /* tier 목록 조회 : default = 제출 횟수 많은 순 */
    @Query(
            value =
                    "SELECT * FROM (\n"
                            + "\tSELECT rownum rn, q.*\n"
                            + "\tFROM (\n"
                            + "\t\tSELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt\n"
                            + "\t\tFROM quiz WHERE quiz_tier=:quizTier\n"
                            + "\t\tORDER BY quiz_submit_cnt desc\n"
                            + "\t) q\n"
                            + ") pg WHERE rn BETWEEN :start AND :end",
            nativeQuery = true)
    public List<Object[]> findListByQuizTier(
            @Param("quizTier") String quizTier,
            @Param("start") Integer start,
            @Param("end") Integer end);

    /* tier 목록 조회 : 정답률 높은 순 */
    @Query(
            value =
                    "SELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt\n"
                            + "\t\tFROM quiz WHERE quiz_tier=:quizTier AND quiz_submit_cnt<>0\n"
                            + "\t\tORDER BY CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END",
            nativeQuery = true)
    public List<Object[]> findByTierOrderByCorrect(@Param("quizTier") String quizTier);

    /* tier 목록 조회 : 정답률 낮은 순 */
    @Query(
            value =
                    "SELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt\n"
                            + "\t\tFROM quiz WHERE quiz_tier=:quizTier AND quiz_submit_cnt<>0\n"
                            + "\t\tORDER BY CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END desc",
            nativeQuery = true)
    public List<Object[]> findByTierOrderByCorrectDesc(@Param("quizTier") String quizTier);
}
