package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.*;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.dto.QuizDTO;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.dto.TestcaseInputDTO;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.quiz.entity.Testcase;
import com.trianglechoke.codesparring.quiz.entity.TestcaseInput;
import com.trianglechoke.codesparring.quiz.repository.QuizRepository;
import com.trianglechoke.codesparring.report.dto.ReportDTO;
import com.trianglechoke.codesparring.report.entity.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired private QuizRepository repository;

    /* quiz 전체 목록 조회 */
    public List<QuizDTO> findAll() throws MyException {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Quiz> quizList = repository.findAll();

        for (Quiz quiz : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(quiz.getQuizNo())
                            .quizTitle(quiz.getQuizTitle())
                            .quizSubmitCnt(quiz.getQuizSubmitCnt())
                            .quizSuccessCnt(quiz.getQuizSuccessCnt())
                            .quizTier(quiz.getQuizTier())
                            .build();
            quizDTOList.add(dto);
        }
        return quizDTOList;
    }

    /* quiz 티어 별 목록 조회 */
    public List<QuizDTO> findByQuizTier(String quizTier) throws MyException {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Object[]> quizList = repository.findListByQuizTier(quizTier);
        for (Object[] objArr : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(Long.valueOf(String.valueOf(objArr[0])))
                            .quizTitle(String.valueOf(objArr[1]))
                            .quizSubmitCnt(Integer.valueOf(String.valueOf(objArr[2])))
                            .quizSuccessCnt(Integer.valueOf(String.valueOf(objArr[3])))
                            .quizTier(quizTier)
                            .build();
            quizDTOList.add(dto);
        }
        return quizDTOList;
    }

    /* quiz 전체 목록 조회_정답률순 정렬 */
    public List<QuizDTO> findOrderByCorrect() throws MyException {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Object[]> quizList = repository.findOrderByCorrect();
        for (Object[] objArr : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(Long.valueOf(String.valueOf(objArr[0])))
                            .quizTitle(String.valueOf(objArr[8]))
                            .quizSubmitCnt(Integer.valueOf(String.valueOf(objArr[5])))
                            .quizSuccessCnt(Integer.valueOf(String.valueOf(objArr[6])))
                            .quizTier(String.valueOf(objArr[7]))
                            .build();
            quizDTOList.add(dto);
        }
        return quizDTOList;
    }

    /* quiz 상세정보 조회 : quiz + reportList + testcaseList */
    public QuizDTO findByQuizNo(Long quizNo) throws MyException {
        Optional<Quiz> optQ = repository.findById(quizNo);
        Quiz quizEntity = optQ.get();
        QuizDTO quizDTO =
                QuizDTO.builder()
                        .quizNo(quizEntity.getQuizNo())
                        .quizTitle(quizEntity.getQuizTitle())
                        .quizContent(quizEntity.getQuizContent())
                        .quizInput(quizEntity.getQuizInput())
                        .quizOutput(quizEntity.getQuizOutput())
                        .quizSubmitCnt(quizEntity.getQuizSubmitCnt())
                        .quizSuccessCnt(quizEntity.getQuizSuccessCnt())
                        .quizTier(quizEntity.getQuizTier())
                        .memberNo(quizEntity.getMember().getMemberNo())
                        .memberName(quizEntity.getMember().getMemberName())
                        .outputType(quizEntity.getOutputType())
                        .build();
        List<ReportDTO> reportDTOList = new ArrayList<>();
        for (Report r : quizEntity.getReportList()) {
            ReportDTO dto =
                    ReportDTO.builder()
                            .reportNo(r.getReportNo())
                            .memberName(r.getMember().getMemberName())
                            .reportComment(r.getReportComment())
                            .build();
            reportDTOList.add(dto);
        }
        quizDTO.setReportDTOList(reportDTOList);
        List<TestcaseDTO> testcaseDTOList = new ArrayList<>();
        for (Testcase tc : quizEntity.getTestcaseList()) {
            TestcaseDTO dto =
                    TestcaseDTO.builder()
                            .testcaseNo(tc.getTestcaseNo())
                            .testcaseOutput(tc.getTestcaseOutput())
                            .build();
            List<TestcaseInputDTO> testcaseInputDTOList = new ArrayList<>();
            for (TestcaseInput input : tc.getTestcaseInputList()) {
                TestcaseInputDTO dtoIn =
                        TestcaseInputDTO.builder()
                                .inputNo(input.getInputNo())
                                .inputVar(input.getInputVar())
                                .testcaseInput(input.getTestcaseInput())
                                .build();
                testcaseInputDTOList.add(dtoIn);
            }
            dto.setTestcaseInputDTOList(testcaseInputDTOList);
            testcaseDTOList.add(dto);
        }
        quizDTO.setTestcaseDTOList(testcaseDTOList);
        return quizDTO;
    }

    /* quiz 추가 */
    public Long addQuiz(QuizDTO quizDTO) throws MyException {
        Member m = Member.builder().memberNo(quizDTO.getMemberNo()).build();
        Quiz quizEntity =
                Quiz.builder()
                        .member(m)
                        .quizTitle(quizDTO.getQuizTitle())
                        .quizContent(quizDTO.getQuizContent())
                        .quizTier("UNRANKED")
                        .quizSubmitCnt(0)
                        .quizSuccessCnt(0)
                        .quizInput(quizDTO.getQuizInput())
                        .quizOutput(quizDTO.getQuizOutput())
                        .outputType(quizDTO.getOutputType())
                        .build();
        repository.save(quizEntity);
        return quizEntity.getQuizNo();
    }

    /* quiz 수정 : title, content, input, output */
    public void modifyQuiz(QuizDTO quizDTO) throws MyException {
        Optional<Quiz> optQ = repository.findById(quizDTO.getQuizNo());
        Quiz quizEntity = optQ.get();
        quizEntity.modifyQuiz(quizDTO);
        repository.save(quizEntity);
    }

    /* 문제 제출 : 문제 제출 횟수 증가, 정답 유무에 따른 정답 횟수 증가 (정답인 경우, correct=true) */
    public void modifyQuizSubmit(QuizDTO quizDTO, boolean correct) throws MyException {
        Optional<Quiz> optQ = repository.findById(quizDTO.getQuizNo());
        Quiz quizEntity = optQ.get();
        quizEntity.modifyQuizSubmit(quizDTO, correct);
        repository.save(quizEntity);
    }

    /* 문제 티어 변경 */
    public void modifyQuizTier(Long quizNo, String tier) throws MyException {
        Optional<Quiz> optQ = repository.findById(quizNo);
        Quiz quizEntity = optQ.get();
        quizEntity.modifyQuizTier(tier);
        repository.save(quizEntity);
    }

    /* quiz 삭제 */
    public void removeQuiz(Long quizNo) throws MyException {
        repository.deleteById(quizNo);
    }
}
