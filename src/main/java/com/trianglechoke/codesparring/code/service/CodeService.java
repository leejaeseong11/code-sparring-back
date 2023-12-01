package com.trianglechoke.codesparring.code.service;

import com.trianglechoke.codesparring.code.dto.CodeTestcaseDTO;
import com.trianglechoke.codesparring.exception.MyException;

import java.util.List;

public interface CodeService {

    /**
     * quizNo에 해당하는 테스트케이스(testcase_no, testcase_output, testcase_input) 가져오기
     *
     * @param quizNo 문제번호
     * @return 문제번호에 해당하는 테스트케이스
     */
    List<CodeTestcaseDTO> findByQuizNo(String quizNo);

    /**
     * MemberCode엔티티에 회원번호, 문제번호, 정답여부 insert
     *
     * @param memberNo 회원번호
     * @param quizNo 문제번호
     * @param correct 정답여부
     */
    void writeMemberCode(Long memberNo, Long quizNo, Integer correct);

    /**
     * Quiz엔티티의 문제제출횟수, 문제정답횟수 수정
     *
     * @param quizNo 문제번호
     * @param correct 정답여부
     * @throws MyException
     */
    void modifyQuizSubmit(Long quizNo, Integer correct) throws MyException;
}
