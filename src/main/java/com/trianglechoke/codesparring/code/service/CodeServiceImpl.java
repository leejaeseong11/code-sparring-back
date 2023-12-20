package com.trianglechoke.codesparring.code.service;

import com.trianglechoke.codesparring.code.dao.CodeRepository;
import com.trianglechoke.codesparring.code.dto.CodeTestcaseDTO;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.util.SecurityUtil;
import com.trianglechoke.codesparring.membercode.dto.MemberCodeDTO;
import com.trianglechoke.codesparring.membercode.entity.MemberCode;
import com.trianglechoke.codesparring.membercode.entity.MemberCodeEmbedded;
import com.trianglechoke.codesparring.quiz.dao.QuizRepository;
import com.trianglechoke.codesparring.quiz.entity.Quiz;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {
    private final CodeRepository codeRepository;
    private final QuizRepository quizRepository;

    // quizNo에 해당하는 testcase_no, testcase_output, testcase_input 가져오기
    @Override
    public List<CodeTestcaseDTO> findByQuizNo(String quizNo) {
        List<CodeTestcaseDTO> ctDTOList = new ArrayList<>();
        List<Object[]> list = codeRepository.findByQuizNo(Long.valueOf(quizNo));

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
    public void writeMemberCode(Long quizNo, Integer correct, String codeUrl) {
        //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime quizDt = LocalDateTime.now();
        System.out.println(quizDt);

        MemberCodeEmbedded embedded =
                MemberCodeEmbedded.builder()
                        .memberNo(SecurityUtil.getCurrentMemberNo())
                        .quizNo(quizNo)
                        .build();

        MemberCode memberCode =
                MemberCode.builder()
                        .id(embedded)
                        .quizCorrect(correct)
                        .quizUrl(codeUrl)
                        .quizDt(quizDt)
                        .build();
        codeRepository.save(memberCode);
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

    // 내가 제출한코드 목록 조회
    @Override
    public List<MemberCodeDTO> findByMemberNo(Long memberNo) {

        List<MemberCodeDTO> mcDTOlist = new ArrayList<>();
        List<Object[]> list = codeRepository.findByMemberNo(memberNo);
        for (Object[] objArr : list) {

            String str=String.valueOf(objArr[3]);
            String[] s=str.split("\\.");

            MemberCodeDTO dto =
                    MemberCodeDTO.builder()
                            .memberNo(memberNo)
                            .quizNo((Long) objArr[1])
                            .quizCorrect((Integer) objArr[2])
                            .quizUrl((String) objArr[4])
                            .quizDt(s[0])
                            .build();
            mcDTOlist.add(dto);
        }
        return mcDTOlist;
    }

    // 내가 제출한 코드 링크 조회
    @Override
    public String findByMemberCodeInfo(Long memberNo, Long quizNo) {
        MemberCode mc = codeRepository.findByQuizUrl(memberNo, quizNo);
        MemberCodeDTO mcDTO = MemberCodeDTO.builder().quizUrl(mc.getQuizUrl()).build();
        return mcDTO.getQuizUrl();
    }
}
