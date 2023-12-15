package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.dto.PageGroup;
import com.trianglechoke.codesparring.quiz.dto.QuizDTO;

import java.util.List;

interface QuizService {
    /**
     * default (제출 횟수 순) 정렬된 전체 문제 목록을 start 부터 end 까지 조회한다.
     *
     * @param currentPage 현재 페이지
     * @return 전체 문제 목록
     * @throws MyException
     */
    public PageGroup<QuizDTO> findQuizList(Integer currentPage) throws MyException;

    /**
     * default (제출 횟수 순) 정렬된 전체 문제 목록을 조회한다.
     *
     * @return 전체 문제 목록
     * @throws MyException
     */
    public List<QuizDTO> findQuizList() throws MyException;

    /**
     * 정답률순으로 정렬된 전체 문제 목록을 조회한다.
     *
     * @param order asc/desc
     * @return 전체 문제 목록
     * @throws MyException
     */
    public List<QuizDTO> findOrderByCorrect(String order) throws MyException;

    /**
     * default (제출 횟수 순) 정렬된 tier 에 해당하는 문제 목록을 start 부터 end 까지 조회한다.
     *
     * @param quizTier 문제 티어
     * @param currentPage 현재 페이지
     * @return 티어 별 문제 목록
     * @throws MyException
     */
    public PageGroup<QuizDTO> findByQuizTier(String quizTier, Integer currentPage)
            throws MyException;

    /**
     * 정답률순으로 정렬된 tier 에 해당하는 문제 목록을 start 부터 end 까지 조회한다.
     *
     * @param quizTier 문제 티어
     * @param order asc/desc
     * @return 티어 별 문제 목록
     * @throws MyException
     */
    public List<QuizDTO> findByTierOrderByCorrect(String quizTier, String order) throws MyException;

    /**
     * quizNo 에 해당하는 문제의 상세 정보를 조회한다.
     *
     * @param quizNo 문제 번호
     * @return 테스트케이스 목록과 신고 커멘트 목록을 포함한 문제 상세 정보
     * @throws MyException
     */
    public QuizDTO findByQuizNo(Long quizNo) throws MyException;

    /**
     * 문제를 추가한다.
     *
     * @param quizDTO 문제 상세정보, 테스트케이스 목록을 담은 객체
     * @return 생성된 문제의 번호
     * @throws MyException
     */
    public Long addQuiz(QuizDTO quizDTO) throws MyException;

    /**
     * 문제의 상세 정보를 수정한다.
     *
     * @param quizDTO 문제 상세정보를 담은 객체
     * @throws MyException
     */
    public void modifyQuiz(QuizDTO quizDTO) throws MyException;

    /**
     * 문제를 삭제한다.
     *
     * @param quizNo 문제 번호
     * @throws MyException
     */
    public void removeQuiz(Long quizNo) throws MyException;
}
