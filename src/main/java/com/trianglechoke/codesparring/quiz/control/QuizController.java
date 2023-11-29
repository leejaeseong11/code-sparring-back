package com.trianglechoke.codesparring.quiz.control;

import com.trianglechoke.codesparring.exception.*;
import com.trianglechoke.codesparring.quiz.dto.QuizDTO;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.service.QuizService;
import com.trianglechoke.codesparring.quiz.service.TestcaseService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired private QuizService service;
    @Autowired private TestcaseService serviceTc;

    /* 문제 전체 목록 조회하기 */
    @GetMapping("/list")
    public List<QuizDTO> quizList() {
        try {
            return service.findAll();
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    /* 문제 목록 티어별로 조회하기 */
    @GetMapping("/list/{quizTier}")
    public List<QuizDTO> quizListByQuizTier(@PathVariable String quizTier) {
        try {
            return service.findByQuizTier(quizTier);
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    //    @GetMapping("/list/correct")
    //    public List<QuizDTO> quizListOrderByCorrect() {
    //        try {
    //            return service.findOrderByCorrect();
    //        } catch (Exception e) {
    //            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
    //        }
    //    }

    /* 문제 상세정보 조회하기 : 관리자 or 회원 */
    /* 회원이 코드 실행 시에도 사용할 Controller */
    @GetMapping("/{quizNo}")
    public QuizDTO quiz(@PathVariable Long quizNo) {
        try {
            return service.findByQuizNo(quizNo);
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_NOT_FOUND);
        }
    }

    /* 문제 추가하기 : 관리자 or 출제 회원 */
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

    /* 문제 수정하기 : 관리자 */
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

    /* 문제 삭제하기 : 관리자 */
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
