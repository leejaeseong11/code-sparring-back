package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.AddException;
import com.trianglechoke.codesparring.exception.FindException;
import com.trianglechoke.codesparring.exception.ModifyException;
import com.trianglechoke.codesparring.exception.RemoveException;
import com.trianglechoke.codesparring.quiz.Repository.TestcaseRepository;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.entity.Testcase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired private TestcaseRepository repository;

    /* quizNo에 해당하는 testcase 목록 조회 */
    public List<TestcaseDTO> findAllByQuizNo(Long quizNo) throws FindException {
        List<TestcaseDTO> tcDTOList = new ArrayList<>();
        List<Object[]> list = repository.findAllByQuizNo(quizNo);
        for (Object[] objArr : list) {
            try {
                TestcaseDTO dto =
                        TestcaseDTO.builder()
                                .testcaseNo(Long.valueOf(String.valueOf(objArr[0])))
                                .quizNo(Long.valueOf(String.valueOf(objArr[1])))
                                .build();
                if (objArr[2] != null) dto.setTestcaseInput(String.valueOf(objArr[2]));
                if (objArr[3] != null) dto.setTestcaseOutput(String.valueOf(objArr[3]));
                tcDTOList.add(dto);
            } catch (Exception e) {
            }
        }
        return tcDTOList;
    }

    /* testcase 추가 */
    public void addTestcase(TestcaseDTO tcDTO) throws AddException {
        Testcase tcEntity =
                Testcase.builder()
                        .quizNo(tcDTO.getQuizNo())
                        .testcaseInput(tcDTO.getTestcaseInput())
                        .testcaseOutput(tcDTO.getTestcaseOutput())
                        .build();
        repository.save(tcEntity);
    }

    /* quizNo에 해당하는 testcase 수정 */
    public void modifyTestcase(TestcaseDTO tcDTO) throws ModifyException {
        Optional<Testcase> optTc = repository.findById(tcDTO.getTestcaseNo());
        Testcase tcEntity = optTc.get();
        tcEntity.modifyInputAndOutput(tcDTO.getTestcaseInput(), tcDTO.getTestcaseOutput());
        repository.save(tcEntity);
    }

    /* quizNo에 해당하는 testcase 삭제 */
    public void removeTestcase(Long testcaseNo) throws RemoveException {
        repository.deleteById(testcaseNo);
    }
}
