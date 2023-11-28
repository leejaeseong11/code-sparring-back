package com.trianglechoke.codesparring.quiz.repository;

import com.trianglechoke.codesparring.quiz.entity.Quiz;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    /* tier 별 조회*/
    @Query(value = "SELECT * FROM quiz WHERE quiz_tier=:quizTier", nativeQuery = true)
    public List<Object[]> findListByQuizTier(String quizTier);

    /* 정답률순 조회 */
    @Query(
            value =
                    "SELECT *\n"
                            + "FROM quiz\n"
                            + "ORDER BY \n"
                            + "    CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END",
            nativeQuery = true)
    public List<Object[]> findOrderByCorrect();
}
