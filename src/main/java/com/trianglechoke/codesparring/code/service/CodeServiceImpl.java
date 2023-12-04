package com.trianglechoke.codesparring.code.service;

import com.trianglechoke.codesparring.code.dao.CodeRepository;
import com.trianglechoke.codesparring.code.dto.CodeTestcaseDTO;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.membercode.entity.MemberCode;
import com.trianglechoke.codesparring.membercode.entity.MemberCodeEmbedded;
import com.trianglechoke.codesparring.quiz.dao.QuizRepository;
import com.trianglechoke.codesparring.quiz.dao.TestcaseInputRepository;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired private CodeRepository repository;
    @Autowired private QuizRepository quizRepository;
    @Autowired private TestcaseInputRepository ti;

    // quizNo에 해당하는 testcase_no, testcase_output, testcase_input 가져오기
    @Override
    public List<CodeTestcaseDTO> findByQuizNo(String quizNo) {
        List<CodeTestcaseDTO> ctDTOList = new ArrayList<>();
        List<Object[]> list = repository.findByQuizNo(Long.valueOf(quizNo));

        for (Object[] objArr : list) {
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

    // MemberCode 회원번호, 문제번호, 정답여부 insert
    @Override
    public void writeMemberCode(Long memberNo, Long quizNo, Integer correct) {
        Member member = Member.builder().memberNo(memberNo).build();
        Quiz quiz = Quiz.builder().quizNo(quizNo).build();
        MemberCodeEmbedded embedded =
                MemberCodeEmbedded.builder().memberNo(memberNo).quizNo(quizNo).build();
        MemberCode memberCode = MemberCode.builder().id(embedded).quizCorrect(correct).build();
        repository.save(memberCode);
        modifyQuizSubmit(quizNo, correct);
    }

    // 문제제출횟수, 문제정답횟수 수정
    @Override
    public void modifyQuizSubmit(Long quizNo, Integer correct) throws MyException {
        Optional<Quiz> optQ = quizRepository.findById(quizNo);
        Quiz quizEntity = optQ.get();
        quizEntity.modifyQuizSubmit(correct);
        quizRepository.save(quizEntity);
    }
}
