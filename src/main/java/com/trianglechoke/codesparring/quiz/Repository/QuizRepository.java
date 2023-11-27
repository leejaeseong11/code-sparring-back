package com.trianglechoke.codesparring.quiz.Repository;

import com.trianglechoke.codesparring.exception.AddException;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Modifying
    @Query(
            value =
                    "INSERT INTO quiz (quiz_no, member_no, quiz_title, quiz_content, quiz_input,"
                            + " quiz_output)\n"
                            + "VALUES (quiz_no_seq.NEXTVAL, :memberNo, :quizTitle, :quizContent,"
                            + " :quizInput, :quizOutput)",
            nativeQuery = true)
    @Transactional
    public void saveQuiz(
            Long memberNo,
            String quizTitle,
            String quizContent,
            String quizInput,
            String quizOutput)
            throws AddException;

    @Query(value = "SELECT * FROM quiz WHERE quiz_tier=:quizTier", nativeQuery = true)
    public List<Object[]> findListByQuizTier(String quizTier); // tier별 조회

    @Query(
            value =
                    "SELECT *\n"
                            + "FROM quiz\n"
                            + "ORDER BY \n"
                            + "    CASE \n"
                            + "        WHEN quiz_success_cnt = 0 THEN NULL\n"
                            + "        ELSE quiz_submit_cnt / quiz_success_cnt\n"
                            + "    END")
    public List<Object[]> findOrderByCorrect(); // 정답률순 조회
}
