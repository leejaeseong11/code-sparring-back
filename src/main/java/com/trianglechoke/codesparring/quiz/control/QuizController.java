package com.trianglechoke.codesparring.quiz.control;

import com.trianglechoke.codesparring.exception.AddException;
import com.trianglechoke.codesparring.exception.FindException;
import com.trianglechoke.codesparring.exception.ModifyException;
import com.trianglechoke.codesparring.exception.RemoveException;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
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

    /* 문제에 해당하는 테스트케이스 목록 조회하기 */
    @GetMapping("/testcase/{quizNo}")
    public List<TestcaseDTO> testcaseList(@PathVariable Long quizNo) throws FindException {
        return service.findAllByQuizNo(quizNo);
    }

    /* 문제에 테스트케이스 추가하기 */
    @PostMapping(value = "/testcase/{quizNo}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> writeTestcase(
            @PathVariable Long quizNo, @RequestBody TestcaseDTO testcaseDTO) throws AddException {
        testcaseDTO.setQuizNo(quizNo);
        try {
            service.addTestcase(testcaseDTO);
            String msg = "테스트케이스 추가 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (AddException e) {
            String msg = "테스트케이스 추가 실패";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

    /* 테스트케이스 수정하기 */
    @PutMapping(value = "/testcase/{testcaseNo}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> modifyTestcase(
            @PathVariable Long testcaseNo, @RequestBody TestcaseDTO testcaseDTO)
            throws ModifyException {
        testcaseDTO.setTestcaseNo(testcaseNo);
        try {
            service.modifyTestcase(testcaseDTO);
            String msg = "테스트케이스 수정 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (ModifyException e) {
            String msg = "테스트케이스 수정 실패";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

    /* 테스트케이스 삭제하기 */
    @DeleteMapping(value = "/testcase/{testcaseNo}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> removeTestcase(@PathVariable Long testcaseNo) throws RemoveException {
        try {
            service.removeTestcase(testcaseNo);
            String msg = "테스트케이스 삭제 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (RemoveException e) {
            String msg = "테스트케이스 수정 실패";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }
}
