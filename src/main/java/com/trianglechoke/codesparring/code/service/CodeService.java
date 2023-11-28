package com.trianglechoke.codesparring.code.service;

import com.trianglechoke.codesparring.code.dto.CodeTestcaseDTO;
import com.trianglechoke.codesparring.code.repository.CodeRepository;
import com.trianglechoke.codesparring.exception.FindException;
import com.trianglechoke.codesparring.quiz.Repository.TestcaseInputRepository;
import com.trianglechoke.codesparring.quiz.Repository.TestcaseRepository;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.entity.Testcase;
import com.trianglechoke.codesparring.quiz.entity.TestcaseInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    @Autowired private CodeRepository repository;
    @Autowired private TestcaseRepository tc;
    @Autowired private TestcaseInputRepository ti;



    //quizNo에 해당하는 testcase_no, testcase_output, testcase_input 가져오기
    public List<CodeTestcaseDTO> findByQuizNo(String quizNo) throws FindException {
        List<CodeTestcaseDTO> ctDTOList = new ArrayList<>();
        List<Object[]> list = repository.findByQuizNo(Long.valueOf(quizNo));

        for(Object[] objArr : list){
            CodeTestcaseDTO dto =
                        CodeTestcaseDTO.builder()
                                .testcaseNo((Long) objArr[0])
                                .testcaseOutput((String) objArr[1])
                                .testcaseInput((String) objArr[2])
                                .build();
            ctDTOList.add(dto);
        }
        return ctDTOList;
    }



}
