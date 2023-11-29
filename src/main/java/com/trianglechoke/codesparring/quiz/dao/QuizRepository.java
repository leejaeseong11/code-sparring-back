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
                            + "\t\tSELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt, quiz_tier\n"
                            + "\t\tFROM quiz ORDER BY quiz_submit_cnt desc\n"
                            + "\t) q\n"
                            + ") pg WHERE rn BETWEEN :start AND :end", nativeQuery = true)
    public List<Object[]> findQuizList(Integer start, Integer end);

    /* 전체 목록 조회 : 정답률 높은 순 */
    @Query(
            value =
                    "SELECT * FROM (\n"
                            + "\tSELECT rownum rn, q.*\n"
                            + "\tFROM (\n"
                            + "\t\tSELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt, quiz_tier\n"
                            + "\t\tFROM quiz ORDER BY CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END\n"
                            + ") q\n"
                            + ") pg WHERE rn BETWEEN :start AND :end",
            nativeQuery = true)
    public List<Object[]> findOrderByCorrect(Integer start, Integer end);

    /* 전체 목록 조회 : 정답률 낮은 순 */
    @Query(
            value =
                    "SELECT * FROM (\n"
                            + "\tSELECT rownum rn, q.*\n"
                            + "\tFROM (\n"
                            + "\t\tSELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt, quiz_tier\n"
                            + "\t\tFROM quiz ORDER BY CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END desc\n"
                            + ") q\n"
                            + ") pg WHERE rn BETWEEN :start AND :end",
            nativeQuery = true)
    public List<Object[]> findOrderByCorrectDesc(Integer start, Integer end);

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
    public List<Object[]> findListByQuizTier(String quizTier, Integer start, Integer end);

    /* tier 목록 조회 : 정답률 높은 순 */
    @Query(
            value =
                    "SELECT * FROM (\n"
                            + "\tSELECT rownum rn, q.*\n"
                            + "\tFROM (\n"
                            + "\t\tSELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt\n"
                            + "\t\tFROM quiz WHERE quiz_tier=:quizTier\n"
                            + "\t\tORDER BY CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END\n"
                            + "\t) q \n"
                            + ") pg WHERE rn BETWEEN :start AND :end",
            nativeQuery = true)
    public List<Object[]> findByTierOrderByCorrect(String quizTier, Integer start, Integer end);

    /* tier 목록 조회 : 정답률 낮은 순 */
    @Query(
            value =
                    "SELECT * FROM (\n"
                            + "\tSELECT rownum rn, q.*\n"
                            + "\tFROM (\n"
                            + "\t\tSELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt\n"
                            + "\t\tFROM quiz WHERE quiz_tier=:quizTier\n"
                            + "\t\tORDER BY CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END desc\n"
                            + "\t) q \n"
                            + ") pg WHERE rn BETWEEN :start AND :end",
            nativeQuery = true)
    public List<Object[]> findByTierOrderByCorrectDesc(String quizTier, Integer start, Integer end);
}
