package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.Repository.TestcaseInputRepository;
import com.trianglechoke.codesparring.quiz.Repository.TestcaseRepository;

import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.dto.TestcaseInputDTO;
import com.trianglechoke.codesparring.quiz.entity.Testcase;
import com.trianglechoke.codesparring.quiz.entity.TestcaseInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        log.error("{}",tcDTO.getTestcaseInputDTOList().size());
        for(TestcaseInputDTO input:tcDTO.getTestcaseInputDTOList()) {
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
    //    public void modifyTestcase(TestcaseDTO tcDTO) throws ModifyException {
    //        Optional<Testcase> optTc = repository.findById(tcDTO.getTestcaseNo());
    //        Testcase tcEntity = optTc.get();
    //        repository.save(tcEntity);
    //    }

    /* testcase 삭제 */
    //    public void removeTestcase(Long testcaseNo) throws RemoveException {
    //        repository.deleteById(testcaseNo);
    //    }
}
