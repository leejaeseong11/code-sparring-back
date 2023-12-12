package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.dao.TestcaseRepository;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.entity.Testcase;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TestcaseServiceImpl implements TestcaseService {
    @Autowired private TestcaseRepository testcaseRepository;

    /* Create : 테스트케이스 추가 */
    public void addTestcase(TestcaseDTO tcDTO) throws MyException {
        Testcase tcEntity =
                Testcase.builder()
                        .quizNo(tcDTO.getQuizNo())
                        .testcaseInput(tcDTO.getTestcaseInput())
                        .testcaseOutput(tcDTO.getTestcaseOutput())
                        .build();
        testcaseRepository.save(tcEntity);
    }

    /* Update : 테스트케이스 수정 */
    public void modifyTestcase(TestcaseDTO tcDTO) throws MyException {
        Optional<Testcase> optTc = testcaseRepository.findById(tcDTO.getTestcaseNo());
        Testcase tcEntity = optTc.get();
        tcEntity.modifyTc(tcDTO.getTestcaseInput(), tcDTO.getTestcaseOutput());
        testcaseRepository.save(tcEntity);
    }

    /* Delete : 테스트케이스 삭제 */
    public void removeTestcase(Long testcaseNo) throws MyException {
        testcaseRepository.deleteById(testcaseNo);
    }
}
