package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.repository.TestcaseInputRepository;
import com.trianglechoke.codesparring.quiz.repository.TestcaseRepository;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.dto.TestcaseInputDTO;
import com.trianglechoke.codesparring.quiz.entity.Testcase;
import com.trianglechoke.codesparring.quiz.entity.TestcaseInput;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TestcaseService {
    @Autowired private TestcaseRepository testcaseRepository;
    @Autowired private TestcaseInputRepository inputRepository;

    /* testcase 추가 */
    public void addTestcase(TestcaseDTO tcDTO) throws MyException {
        Testcase tcEntity =
                Testcase.builder()
                        .quizNo(tcDTO.getQuizNo())
                        .testcaseOutput(tcDTO.getTestcaseOutput())
                        .build();
        testcaseRepository.save(tcEntity);
        log.error("{}", tcDTO.getTestcaseInputDTOList().size());
        for (TestcaseInputDTO input : tcDTO.getTestcaseInputDTOList()) {
            TestcaseInput inputEntity =
                    TestcaseInput.builder()
                            .testcaseNo(tcEntity.getTestcaseNo())
                            .inputVar(input.getInputVar())
                            .testcaseInput(input.getTestcaseInput())
                            .build();
            inputRepository.save(inputEntity);
        }
    }

    /* testcase 수정 */
    public void modifyTestcase(TestcaseDTO tcDTO) throws MyException {
        Optional<Testcase> optTc = testcaseRepository.findById(tcDTO.getTestcaseNo());
        Testcase tcEntity = optTc.get();
        tcEntity.modifyOutput(tcDTO.getTestcaseOutput());
        for (int i = 0; i < tcDTO.getTestcaseInputDTOList().size(); i++) {
            tcEntity.getTestcaseInputList()
                    .get(i)
                    .modifyInput(tcDTO.getTestcaseInputDTOList().get(i));
        }
        testcaseRepository.save(tcEntity);
    }

    /* testcase 삭제 */
    public void removeTestcase(Long testcaseNo) throws MyException {
        testcaseRepository.deleteById(testcaseNo);
    }
}
