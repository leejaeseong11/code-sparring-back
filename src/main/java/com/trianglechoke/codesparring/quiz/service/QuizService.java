package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.dto.QuizDTO;

import java.util.List;

interface QuizService {
    /**
     * default (제출 횟수 순) 정렬된 전체 문제 목록을 start 부터 end 까지 조회한다.
     * @param start 시작
     * @param end 끝
     * @return 전체 문제 목록
     * @throws MyException
     */
    public List<QuizDTO> findQuizList(Integer start, Integer end) throws MyException;

    /**
     * 정답률순으로 정렬된 전체 문제 목록을 start 부터 end 까지 조회한다.
     * @param start 시작
     * @param end 끝
     * @param order asc/desc
     * @return 전체 문제 목록
     * @throws MyException
     */
    public List<QuizDTO> findOrderByCorrect(Integer start, Integer end, String order) throws MyException;

    /**
     * tier 에 해당하는 문제 목록을 start 부터 end 까지 조회한다.
     * @param quizTier 문제 티어
     * @param start 시작 인덱스
     * @param end 끝 인덱스
     * @return 문제 목록
     * @throws MyException
     */
    public List<QuizDTO> findByQuizTier(String quizTier, Integer start, Integer end) throws MyException;
}
