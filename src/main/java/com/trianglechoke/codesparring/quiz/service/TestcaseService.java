package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;

public interface TestcaseService {
    /**
     * 테스트케이스를 추가한다.
     *
     * @param tcDTO 테스트케이스, 테스트케이스 입력값 목록을 담은 객체
     * @throws MyException
     */
    public void addTestcase(TestcaseDTO tcDTO) throws MyException;

    /**
     * 테스트케이스를 수정한다.
     *
     * @param tcDTO 테스트케이스, 테스트케이스 입력값 목록을 담은 객체
     * @throws MyException
     */
    public void modifyTestcase(TestcaseDTO tcDTO) throws MyException;

    /**
     * 테스트케이스를 삭제한다.
     *
     * @param testcaseNo 테스트케이스 번호
     * @throws MyException
     */
    public void removeTestcase(Long testcaseNo) throws MyException;
}
