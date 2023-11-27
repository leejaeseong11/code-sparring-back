package com.trianglechoke.codesparring.quiz.control;

import com.trianglechoke.codesparring.exception.*;
import com.trianglechoke.codesparring.quiz.dto.QuizDTO;
import com.trianglechoke.codesparring.quiz.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired private QuizService service;

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

    @GetMapping("/list/correct")
    public List<QuizDTO> quizListOrderByCorrect() {
        try {
            return service.findOrderByCorrect();
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_LIST_NOT_FOUND);
        }
    }

    /* 문제 상세정보 조회하기 : 관리자 or 회원 */
    /* 회원이 코드 실행 시에도 사용할 Controller */
    @GetMapping("/{quizNo}")
    public QuizDTO quiz(@PathVariable Long quizNo) throws MyException {
        try {
            return service.findByQuizNo(quizNo);
        } catch (Exception e) {
            throw new MyException(ErrorCode.QUIZ_NOT_FOUND);
        }
    }

    /* 문제 추가하기 : 관리자 or 출제 회원 */
    @PostMapping()
    public ResponseEntity<?> writeQuiz(@RequestBody QuizDTO quizDTO) throws AddException {
        try {
            service.addQuiz(quizDTO);
            String msg = "문제 출제 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (AddException e) {
            throw new AddException("문제 출제 실패");
        }
    }

    /* 문제 수정하기 : 관리자 */
    @PutMapping("/{quizNo}")
    public ResponseEntity<?> modifyQuiz(@PathVariable Long quizNo, @RequestBody QuizDTO quizDTO)
            throws ModifyException {
        quizDTO.setQuizNo(quizNo);
        try {
            service.modifyQuiz(quizDTO);
            String msg = "문제 수정 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (ModifyException e) {
            throw new ModifyException("문제 수정 실패");
        }
    }

    /* 문제 삭제하기 : 관리자 */
    @DeleteMapping("/{quizNo}")
    public ResponseEntity<?> removeQuiz(@PathVariable Long quizNo) throws RemoveException {
        try {
            service.removeQuiz(quizNo);
            String msg = "문제 삭제 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (RemoveException e) {
            throw new RemoveException("문제 삭제 실패");
        }
    }
}
