package com.trianglechoke.codesparring.quiz.Repository;

import com.trianglechoke.codesparring.exception.AddException;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

    //    @Query(value = "")
    //    public List<Object[]> findByQuizTier() throws FindException;
}
