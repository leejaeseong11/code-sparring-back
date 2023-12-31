package com.trianglechoke.codesparring.quiz.control;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.service.TestcaseServiceImpl;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testcase")
public class TestcaseController {
    @Autowired private TestcaseServiceImpl service;

    /* 테스트케이스 추가하기 */
    @PostMapping("/{quizNo}")
    @Transactional
    public ResponseEntity<?> writeTestcase(
            @PathVariable Long quizNo, @RequestBody TestcaseDTO testcaseDTO) {
        testcaseDTO.setQuizNo(quizNo);
        try {
            service.addTestcase(testcaseDTO);
            String msg = "테스트케이스 추가 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.TESTCASE_NOT_SAVED);
        }
    }

    /* 테스트케이스 수정하기 */
    @PutMapping("/{testcaseNo}")
    @Transactional
    public ResponseEntity<?> modifyTestcase(
            @PathVariable Long testcaseNo, @RequestBody TestcaseDTO testcaseDTO) {
        testcaseDTO.setTestcaseNo(testcaseNo);
        try {
            service.modifyTestcase(testcaseDTO);
            String msg = "테스트케이스 수정 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.TESTCASE_NOT_MODIFIED);
        }
    }

    /* 테스트케이스 삭제하기 */
    @DeleteMapping("/{testcaseNo}")
    @Transactional
    public ResponseEntity<?> removeTestcase(@PathVariable Long testcaseNo) {
        try {
            service.removeTestcase(testcaseNo);
            String msg = "테스트케이스 삭제 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.TESTCASE_NOT_FOUND);
        }
    }
}
