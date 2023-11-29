package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.*;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.dto.QuizDTO;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.dto.TestcaseInputDTO;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.quiz.entity.Testcase;
import com.trianglechoke.codesparring.quiz.entity.TestcaseInput;
import com.trianglechoke.codesparring.quiz.dao.QuizRepository;
import com.trianglechoke.codesparring.report.dto.ReportDTO;
import com.trianglechoke.codesparring.report.entity.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired private QuizRepository repository;

    /* Read : 전체 목록 조회 - default */
    public List<QuizDTO> findQuizList(Integer start, Integer end) throws MyException {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Object[]> quizList = repository.findQuizList(start, end);
        for (Object[] objArr : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(Long.valueOf(String.valueOf(objArr[1])))
                            .quizTitle(String.valueOf(objArr[2]))
                            .quizSubmitCnt(Integer.valueOf(String.valueOf(objArr[3])))
                            .quizSuccessCnt(Integer.valueOf(String.valueOf(objArr[4])))
                            .quizTier(String.valueOf(objArr[5]))
                            .build();
            quizDTOList.add(dto);
        }
        return quizDTOList;
    }

    /* Read : 전체 목록 조회 - 정답률순 */
    public List<QuizDTO> findOrderByCorrect(Integer start, Integer end, String order)
            throws MyException {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Object[]> quizList = new ArrayList<>();
        if (order.equals("asc")) quizList = repository.findOrderByCorrect(start, end);
        else if (order.equals("desc")) quizList = repository.findOrderByCorrectDesc(start, end);

        for (Object[] objArr : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(Long.valueOf(String.valueOf(objArr[1])))
                            .quizTitle(String.valueOf(objArr[2]))
                            .quizSubmitCnt(Integer.valueOf(String.valueOf(objArr[3])))
                            .quizSuccessCnt(Integer.valueOf(String.valueOf(objArr[4])))
                            .quizTier(String.valueOf(objArr[5]))
                            .build();
            quizDTOList.add(dto);
        }
        return quizDTOList;
    }

    /* Read : 티어 별 목록 조회 - default */
    public List<QuizDTO> findByQuizTier(String quizTier, Integer start, Integer end)
            throws MyException {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Object[]> quizList = repository.findListByQuizTier(quizTier, start, end);
        for (Object[] objArr : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(Long.valueOf(String.valueOf(objArr[1])))
                            .quizTitle(String.valueOf(objArr[2]))
                            .quizSubmitCnt(Integer.valueOf(String.valueOf(objArr[3])))
                            .quizSuccessCnt(Integer.valueOf(String.valueOf(objArr[4])))
                            .quizTier(quizTier)
                            .build();
            quizDTOList.add(dto);
        }
        return quizDTOList;
    }

    /* Read : 티어 별 목록 조회 - 정답률순 */
    public List<QuizDTO> findByTierOrderByCorrect(String quizTier, Integer start, Integer end, String order)
            throws MyException {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Object[]> quizList = new ArrayList<>();
        if (order.equals("asc")) quizList = repository.findByTierOrderByCorrect(quizTier, start, end);
        else if (order.equals("desc")) quizList = repository.findByTierOrderByCorrectDesc(quizTier, start, end);

        for (Object[] objArr : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(Long.valueOf(String.valueOf(objArr[1])))
                            .quizTitle(String.valueOf(objArr[2]))
                            .quizSubmitCnt(Integer.valueOf(String.valueOf(objArr[3])))
                            .quizSuccessCnt(Integer.valueOf(String.valueOf(objArr[4])))
                            .quizTier(quizTier)
                            .build();
            quizDTOList.add(dto);
        }
        return quizDTOList;
    }

    /* Read : 문제 상세 조회 */
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

    /* Create : 문제 추가 */
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

    /* Update : 문제의 title, content, input, output 수정 */
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
