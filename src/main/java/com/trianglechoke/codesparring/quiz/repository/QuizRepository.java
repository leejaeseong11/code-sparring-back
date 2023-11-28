package com.trianglechoke.codesparring.quiz.repository;

import com.trianglechoke.codesparring.quiz.entity.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query(value = "SELECT *\n" +
            "\t\tFROM (\n" +
            "\t\t\tSELECT rownum rn, q.*\n" +
            "\t\t\tFROM (\n" +
            "\t\t\t\tSELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt, quiz_tier\n" +
            "\t\t\t\tFROM quiz ORDER BY quiz_no\n" +
            "\t\t\t) q\n" +
            "\t\t) pg\n" +
            "WHERE rn BETWEEN :start AND :end", nativeQuery = true)
    public List<Object[]> findOrderByQuizNo(Integer start, Integer end);

    /* tier 별 조회*/
    @Query(value = "SELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt FROM quiz " +
            "WHERE quiz_tier=:quizTier", nativeQuery = true)
    public List<Object[]> findListByQuizTier(String quizTier);

    /* 정답률순 조회 */
    @Query(
            value =
                    "SELECT quiz_no, quiz_title, quiz_submit_cnt, quiz_success_cnt, quiz_tier\n"
                            + "FROM quiz\n"
                            + "ORDER BY \n"
                            + "    CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END",
            nativeQuery = true)
    public List<Object[]> findOrderByCorrect();
}
