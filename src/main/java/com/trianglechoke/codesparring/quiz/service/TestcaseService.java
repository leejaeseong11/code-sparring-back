package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.AddException;
import com.trianglechoke.codesparring.exception.FindException;
import com.trianglechoke.codesparring.quiz.Repository.TestcaseRepository;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.entity.Testcase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestcaseService {
    @Autowired private TestcaseRepository repository;

    /*
    private List<TestcaseDTO> findAllById(Long quizNo) throws FindException {
        List<Object[]> objectList=repository.findAllByQuizNo(quizNo);
        List<TestcaseDTO> tcDTOList=new ArrayList<>();

        return null;
    }
    */

    private void addTestcase(TestcaseDTO tcDTO) throws AddException {
        Testcase tcEntity=Testcase.builder()
                .testcaseInput(tcDTO.getTestcaseInput())
                .testcaseOutput(tcDTO.getTestcaseOutput())
                .build();
    }

}
