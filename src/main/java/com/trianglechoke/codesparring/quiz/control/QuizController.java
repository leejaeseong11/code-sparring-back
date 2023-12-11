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
    @GetMapping("/list/{currentPage}")
    public PageGroup<QuizDTO> quizList(@PathVariable Integer currentPage) {
        try {
            PageGroup<QuizDTO> list = service.findQuizList(currentPage);
            if (list.getList().size() == 0) throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
            else return list;
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    /* 문제 전체 목록 조회하기 : 정답률 */
    @GetMapping("/list/{currentPage}/{order}")
    public PageGroup<QuizDTO> quizListOrderByCorrect(
            @PathVariable Integer currentPage, @PathVariable String order) {
        try {
            PageGroup<QuizDTO> list =
                    service.findOrderByCorrect(currentPage, order);
            if (list.getList().size() == 0) throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
            else return list;
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    /* 티어 별 문제 목록 조회하기 : default */
    @GetMapping("/tier/{quizTier}/{currentPage}")
    public PageGroup<QuizDTO> quizListByQuizTier(
            @PathVariable String quizTier, @PathVariable Integer currentPage) {
        try {
            PageGroup<QuizDTO> list =
                    service.findByQuizTier(quizTier, currentPage);
            if (list.getList().size() == 0) throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
            else return list;
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    /* 티어 별 문제 목록 조회하기 : 정답률 */
    @GetMapping("/tier/{quizTier}/{currentPage}/{order}")
    public PageGroup<QuizDTO> quizListByQuizTier(
            @PathVariable String quizTier,
            @PathVariable Integer currentPage,
            @PathVariable String order) {
        try {
            PageGroup<QuizDTO> list =
                    service.findByTierOrderByCorrect(
                            quizTier, currentPage, order);
            if (list.getList().size() <= 0) throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
            else return list;
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    /* 문제 상세 정보 조회하기 */
    @GetMapping("/{quizNo}")
    public QuizDTO quiz(@PathVariable Long quizNo) {
        try {
            return service.findByQuizNo(quizNo);
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_NOT_SAVED);
        }
    }

    /* 문제 상세 정보 수정하기 */
    @PutMapping("/{quizNo}")
    @Transactional
    public ResponseEntity<?> modifyQuiz(@PathVariable Long quizNo, @RequestBody QuizDTO quizDTO) {
        quizDTO.setQuizNo(quizNo);
        try {
            service.modifyQuiz(quizDTO);
            String msg = "문제 수정 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_NOT_MODIFIED);
        }
    }

    /* 문제 삭제하기 */
    @DeleteMapping("/{quizNo}")
    @Transactional
    public ResponseEntity<?> removeQuiz(@PathVariable Long quizNo) {
        try {
            service.removeQuiz(quizNo);
            String msg = "문제 삭제 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_NOT_FOUND);
        }
    }
}
