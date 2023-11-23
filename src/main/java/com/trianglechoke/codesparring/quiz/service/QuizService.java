package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.AddException;
import com.trianglechoke.codesparring.exception.FindException;
import com.trianglechoke.codesparring.exception.ModifyException;
import com.trianglechoke.codesparring.exception.RemoveException;
import com.trianglechoke.codesparring.quiz.Repository.QuizRepository;
import com.trianglechoke.codesparring.quiz.dto.QuizDTO;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.quiz.entity.Testcase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired private QuizRepository repository;

    /* quiz 전체 목록 조회 */
    public List<QuizDTO> findAll() throws FindException {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Quiz> quizList = repository.findAll();
        for (Quiz quiz : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(quiz.getQuizNo())
                            .quizTitle(quiz.getQuizTitle())
                            .quizSubmitCnt(quiz.getQuizSubmitCnt())
                            .quizSuccessCnt(quiz.getQuizSuccessCnt())
                            .quizTier(quiz.getQuizTier())
                            .build();
            quizDTOList.add(dto);
        }
        return quizDTOList;
    }

    /* TODO - quiz 필터 별 목록 조회 -> 추후 추가할 예정 : 검증된 문제 목록, 회원 출제 문제 목록, 등등 */

    /* quiz 상세정보 조회 : quiz + testcaseList */
    public QuizDTO findByQuizNo(Long quizNo) throws FindException {
        Optional<Quiz> optQ = repository.findById(quizNo);
        Quiz quizEntity = optQ.get();
        QuizDTO quizDTO =
                QuizDTO.builder()
                        .quizNo(quizEntity.getQuizNo())
                        .quizTitle(quizEntity.getQuizTitle())
                        .quizContent(quizEntity.getQuizContent())
                        .quizInput(quizEntity.getQuizInput())
                        .quizOutput(quizEntity.getQuizOutput())
                        .quizSubmitCnt(quizEntity.getQuizSubmitCnt())
                        .quizSuccessCnt(quizEntity.getQuizSuccessCnt())
                        .quizTier(quizEntity.getQuizTier())
                        .memberNo((quizEntity.getMember().getMemberNo()))
                        .build();
        List<TestcaseDTO> testcaseDTOList = new ArrayList<>();
        for (Testcase tc : quizEntity.getTestcaseList()) {
            TestcaseDTO dto =
                    TestcaseDTO.builder()
                            .testcaseNo(tc.getTestcaseNo())
                            .testcaseInput(tc.getTestcaseInput())
                            .testcaseOutput(tc.getTestcaseOutput())
                            .build();
            testcaseDTOList.add(dto);
        }
        quizDTO.setTestcaseDTOList(testcaseDTOList);
        // TODO - reportList 추가
        return quizDTO;
    }

    /* quiz 추가 */
    public void addQuiz(QuizDTO quizDTO) throws AddException {
        repository.saveQuiz(
                quizDTO.getMemberNo(),
                quizDTO.getQuizTitle(),
                quizDTO.getQuizContent(),
                quizDTO.getQuizInput(),
                quizDTO.getQuizOutput());
    }

    /* quiz 수정 : title, content, input, output */
    public void modifyQuiz(QuizDTO quizDTO) throws ModifyException {
        Optional<Quiz> optQ = repository.findById(quizDTO.getQuizNo());
        Quiz quizEntity = optQ.get();
        quizEntity.modifyQuiz(quizDTO);
        repository.save(quizEntity);
    }

    /* TODO - 문제 제출 횟수, 정답 횟수 증가, 티어 변경 등 추후 추가 */

    /* quiz 삭제 */
    public void removeQuiz(Long quizNo) throws RemoveException {
        repository.deleteById(quizNo);
    }
}
