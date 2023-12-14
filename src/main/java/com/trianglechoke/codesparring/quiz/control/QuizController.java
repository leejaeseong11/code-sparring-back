package com.trianglechoke.codesparring.quiz.control;

import com.trianglechoke.codesparring.exception.*;
import com.trianglechoke.codesparring.quiz.dto.PageGroup;
import com.trianglechoke.codesparring.quiz.dto.QuizDTO;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.service.QuizServiceImpl;
import com.trianglechoke.codesparring.quiz.service.TestcaseServiceImpl;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired private QuizServiceImpl service;
    @Autowired private TestcaseServiceImpl serviceTc;

    /* 문제 전체 목록 조회하기 : default */
    @GetMapping("/list")
    public List<QuizDTO> quizList() {
        try {
            List<QuizDTO> list = service.findQuizList();
            if (list.size() == 0) throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
            else return list;
        } catch (MyException e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    /* 문제 전체 목록 조회하기 : 정답률 */
    @GetMapping("/list/{order}")
    public List<QuizDTO> quizListOrderByCorrect(@PathVariable String order) {
        try {
            List<QuizDTO> list = service.findOrderByCorrect(order);
            if (list.size() == 0) throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
            else return list;
        } catch (MyException e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    /* 티어 별 문제 목록 조회하기 : default */
    @GetMapping("/tier/{quizTier}")
    public List<QuizDTO> quizListByQuizTier(
            @PathVariable String quizTier) {
        try {
            List<QuizDTO> list = service.findByQuizTier(quizTier);
            if (list.size() == 0) throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
            else return list;
        } catch (MyException e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    /* 티어 별 문제 목록 조회하기 : 정답률 */
    @GetMapping("/tier/{quizTier}/{order}")
    public List<QuizDTO> quizListByQuizTier(
            @PathVariable String quizTier,
            @PathVariable String order) {
        try {
            List<QuizDTO> list =
                    service.findByTierOrderByCorrect(quizTier, order);
            if (list.size() <= 0) throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
            else return list;
        } catch (MyException e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    /* 문제 상세 정보 조회하기 */
    @GetMapping("/{quizNo}")
    public QuizDTO quiz(@PathVariable Long quizNo) {
        try {
            return service.findByQuizNo(quizNo);
        } catch (MyException e) {
            throw new MyException(ErrorCode.QUIZ_NOT_FOUND);
        }
    }

    /* 문제 추가하기 */
    @PostMapping()
    @Transactional
    public ResponseEntity<?> writeQuiz(@RequestBody QuizDTO quizDTO) {
        try {
            Long quizNo = service.addQuiz(quizDTO);
            for (TestcaseDTO tcDTO : quizDTO.getTestcaseDTOList()) {
                tcDTO.setQuizNo(quizNo);
                serviceTc.addTestcase(tcDTO);
            }
            String msg = "문제 출제 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (MyException e) {
            throw new MyException(ErrorCode.QUIZ_NOT_SAVED);
        }
    }
}
