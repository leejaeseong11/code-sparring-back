package com.trianglechoke.codesparring.quiz.control;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.service.TestcaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testcase")
public class TestcaseController {
    @Autowired private TestcaseService service;

    /* 문제에 해당하는 테스트케이스 목록 조회하기 : 관리자 */
    //    @GetMapping("/{quizNo}")
    //    public List<TestcaseDTO> testcaseList(@PathVariable Long quizNo) throws FindException {
    //        return service.findAllByQuizNo(quizNo);
    //    }

    /* 문제에 테스트케이스 추가하기 : 관리자 */
    @PostMapping("/{quizNo}")
    public ResponseEntity<?> writeTestcase(
            @PathVariable Long quizNo, @RequestBody TestcaseDTO testcaseDTO) throws MyException {
        testcaseDTO.setQuizNo(quizNo);
        try {
            service.addTestcase(testcaseDTO);
            String msg = "테스트케이스 추가 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.TESTCASE_NOT_SAVED);
        }
    }

    /* 테스트케이스 수정하기 : 관리자 */
    @PutMapping("/{testcaseNo}")
    public ResponseEntity<?> modifyTestcase(
            @PathVariable Long testcaseNo, @RequestBody TestcaseDTO testcaseDTO)
            throws MyException {
        testcaseDTO.setTestcaseNo(testcaseNo);
        try {
            service.modifyTestcase(testcaseDTO);
            String msg = "테스트케이스 수정 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.TESTCASE_NOT_MODIFIED);
        }
    }

    /* 테스트케이스 삭제하기 : 관리자 */
    @DeleteMapping("/{testcaseNo}")
    public ResponseEntity<?> removeTestcase(@PathVariable Long testcaseNo) throws MyException {
        try {
            service.removeTestcase(testcaseNo);
            String msg = "테스트케이스 삭제 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.TESTCASE_NOT_FOUND);
        }
    }
}
