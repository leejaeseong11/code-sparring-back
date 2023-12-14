package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.exception.*;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.dao.QuizRepository;
import com.trianglechoke.codesparring.quiz.dto.PageGroup;
import com.trianglechoke.codesparring.quiz.dto.QuizDTO;
import com.trianglechoke.codesparring.quiz.dto.TestcaseDTO;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.quiz.entity.Testcase;
import com.trianglechoke.codesparring.report.dto.ReportDTO;
import com.trianglechoke.codesparring.report.entity.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired private QuizRepository repository;

    /* Read : 전체 목록 페이징 조회 - default */
    public PageGroup<QuizDTO> findQuizList(Integer currentPage) throws MyException {
        if (currentPage < 1) currentPage = 1;
        int cntPerPage = 10;

        int start;
        int end;
        end = currentPage * cntPerPage;
        start = (currentPage - 1) * cntPerPage + 1;

        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Object[]> quizList = repository.findQuizList(start, end);
        Long quizCnt = repository.count();
        for (Object[] objArr : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(Long.valueOf(String.valueOf(objArr[1])))
                            .quizTitle(String.valueOf(objArr[2]))
                            .quizSubmitCnt(Integer.valueOf(String.valueOf(objArr[3])))
                            .quizSuccessCnt(Integer.valueOf(String.valueOf(objArr[4])))
                            .quizTier(String.valueOf(objArr[5]))
                            .build();
            if (dto.getQuizSubmitCnt() == 0) {
                dto.setQuizCorrectPercent("-");
            } else if (dto.getQuizSuccessCnt() == 0) {
                dto.setQuizCorrectPercent("0.00%");
            } else {
                double tmp =
                        (double) dto.getQuizSuccessCnt() / (double) dto.getQuizSubmitCnt() * 100;
                BigDecimal result = new BigDecimal(tmp).setScale(2, RoundingMode.HALF_UP);
                dto.setQuizCorrectPercent(result + "%");
            }
            quizDTOList.add(dto);
        }
        PageGroup<QuizDTO> pg = new PageGroup<>(quizDTOList, currentPage, quizCnt);
        return pg;
    }

    /* Read : 전체 목록 조회 - default */
    public List<QuizDTO> findQuizList() throws MyException {
        Sort sort = Sort.by(
                Sort.Order.desc("quizSubmitCnt").nullsLast(),
                Sort.Order.desc("quizSuccessCnt").nullsLast()
        );
        List<Quiz> quizList=repository.findAll(sort);
        List<QuizDTO> quizDTOList=new ArrayList<>();
        for(Quiz quiz: quizList) {
            QuizDTO dto=QuizDTO.builder()
                    .quizTitle(quiz.getQuizTitle())
                    .quizNo(quiz.getQuizNo())
                    .quizTier(quiz.getQuizTier())
                    .quizSubmitCnt(quiz.getQuizSubmitCnt())
                    .build();
            if (quiz.getQuizSubmitCnt() == 0) {
                dto.setQuizCorrectPercent("-");
            } else if (quiz.getQuizSuccessCnt() == 0) {
                dto.setQuizCorrectPercent("0.00%");
            } else {
                double tmp =
                        (double) quiz.getQuizSuccessCnt() / (double) quiz.getQuizSubmitCnt() * 100;
                BigDecimal result = new BigDecimal(tmp).setScale(2, RoundingMode.HALF_UP);
                dto.setQuizCorrectPercent(result + "%");
            }
            quizDTOList.add(dto);
        }
        return quizDTOList;
    }

    /* Read : 전체 목록 조회 - 정답률순 */
    public List<QuizDTO> findOrderByCorrect(String order)
            throws MyException {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Object[]> quizList = new ArrayList<>();
        if (order.equals("asc")) quizList = repository.findOrderByCorrect();
        else if (order.equals("desc")) quizList = repository.findOrderByCorrectDesc();
        Long quizCnt = repository.count();
        for (Object[] objArr : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(Long.valueOf(String.valueOf(objArr[0])))
                            .quizTitle(String.valueOf(objArr[1]))
                            .quizSubmitCnt(Integer.valueOf(String.valueOf(objArr[2])))
                            .quizSuccessCnt(Integer.valueOf(String.valueOf(objArr[3])))
                            .quizTier(String.valueOf(objArr[4]))
                            .build();
            if (dto.getQuizSubmitCnt() == 0) {
                dto.setQuizCorrectPercent("-");
            } else if (dto.getQuizSuccessCnt() == 0) {
                dto.setQuizCorrectPercent("0.00%");
            } else {
                double tmp =
                        (double) dto.getQuizSuccessCnt() / (double) dto.getQuizSubmitCnt() * 100;
                BigDecimal result = new BigDecimal(tmp).setScale(2, RoundingMode.HALF_UP);
                dto.setQuizCorrectPercent(result + "%");
            }
            quizDTOList.add(dto);
        }
        return quizDTOList;
    }

    /* Read : 티어 별 목록 페이징 조회 - default */
    public PageGroup<QuizDTO> findByQuizTier(String quizTier, Integer currentPage)
            throws MyException {
        if (currentPage < 1) currentPage = 1;
        int cntPerPage = 10;

        int start;
        int end;
        end = currentPage * cntPerPage;
        start = (currentPage - 1) * cntPerPage + 1;

        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Object[]> quizList = repository.findListByQuizTier(quizTier, start, end);
        Quiz exampleQuiz = Quiz.builder().quizTier(quizTier).build();
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll();
        Example<Quiz> example = Example.of(exampleQuiz, exampleMatcher);
        Long quizCnt = repository.count(example);
        for (Object[] objArr : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(Long.valueOf(String.valueOf(objArr[1])))
                            .quizTitle(String.valueOf(objArr[2]))
                            .quizSubmitCnt(Integer.valueOf(String.valueOf(objArr[3])))
                            .quizSuccessCnt(Integer.valueOf(String.valueOf(objArr[4])))
                            .quizTier(quizTier)
                            .build();
            if (dto.getQuizSubmitCnt() == 0) {
                dto.setQuizCorrectPercent("-");
            } else if (dto.getQuizSuccessCnt() == 0) {
                dto.setQuizCorrectPercent("0.00%");
            } else {
                double tmp =
                        (double) dto.getQuizSuccessCnt() / (double) dto.getQuizSubmitCnt() * 100;
                BigDecimal result = new BigDecimal(tmp).setScale(2, RoundingMode.HALF_UP);
                dto.setQuizCorrectPercent(result + "%");
            }
            quizDTOList.add(dto);
        }
        PageGroup<QuizDTO> pg = new PageGroup<>(quizDTOList, currentPage, quizCnt);
        return pg;
    }

    /* Read : 티어 별 목록 조회 - default */
    public List<QuizDTO> findByQuizTier(String quizTier) throws MyException {
        Quiz exampleQuiz = Quiz.builder().quizTier(quizTier).build();
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll();
        Example<Quiz> example = Example.of(exampleQuiz, exampleMatcher);
        Sort sort = Sort.by(
                Sort.Order.desc("quizSubmitCnt").nullsLast(),
                Sort.Order.desc("quizSuccessCnt").nullsLast()
        );
        List<Quiz> list=repository.findAll(example, sort);
        List<QuizDTO> dtoList=new ArrayList<>();
        for(Quiz quiz:list) {
            QuizDTO dto=
                    QuizDTO.builder()
                            .quizTitle(quiz.getQuizTitle())
                            .quizNo(quiz.getQuizNo())
                            .quizTier(quiz.getQuizTier())
                            .quizSubmitCnt(quiz.getQuizSubmitCnt())
                            .build();
            if (quiz.getQuizSubmitCnt() == 0) {
                dto.setQuizCorrectPercent("-");
            } else if (quiz.getQuizSuccessCnt() == 0) {
                dto.setQuizCorrectPercent("0.00%");
            } else {
                double tmp =
                        (double) quiz.getQuizSuccessCnt() / (double) quiz.getQuizSubmitCnt() * 100;
                BigDecimal result = new BigDecimal(tmp).setScale(2, RoundingMode.HALF_UP);
                dto.setQuizCorrectPercent(result + "%");
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    /* Read : 티어 별 목록 조회 - 정답률순 */
    public List<QuizDTO> findByTierOrderByCorrect(
            String quizTier, String order) throws MyException {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        List<Object[]> quizList = new ArrayList<>();
        if (order.equals("asc"))
            quizList = repository.findByTierOrderByCorrect(quizTier);
        else if (order.equals("desc"))
            quizList = repository.findByTierOrderByCorrectDesc(quizTier);
        for (Object[] objArr : quizList) {
            QuizDTO dto =
                    QuizDTO.builder()
                            .quizNo(Long.valueOf(String.valueOf(objArr[0])))
                            .quizTitle(String.valueOf(objArr[1]))
                            .quizSubmitCnt(Integer.valueOf(String.valueOf(objArr[2])))
                            .quizSuccessCnt(Integer.valueOf(String.valueOf(objArr[3])))
                            .quizTier(quizTier)
                            .build();
            if (dto.getQuizSubmitCnt() == 0) {
                dto.setQuizCorrectPercent("-");
            } else if (dto.getQuizSuccessCnt() == 0) {
                dto.setQuizCorrectPercent("0.00%");
            } else {
                double tmp =
                        (double) dto.getQuizSuccessCnt() / (double) dto.getQuizSubmitCnt() * 100;
                BigDecimal result = new BigDecimal(tmp).setScale(2, RoundingMode.HALF_UP);
                dto.setQuizCorrectPercent(result + "%");
            }
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
                        .build();
        if (quizDTO.getQuizSubmitCnt() == 0) {
            quizDTO.setQuizCorrectPercent("-");
        } else if (quizDTO.getQuizSuccessCnt() == 0) {
            quizDTO.setQuizCorrectPercent("0.00%");
        } else {
            double tmp =
                    (double) quizDTO.getQuizSuccessCnt()
                            / (double) quizDTO.getQuizSubmitCnt()
                            * 100;
            BigDecimal result = new BigDecimal(tmp).setScale(2, RoundingMode.HALF_UP);
            quizDTO.setQuizCorrectPercent(result + "%");
        }
        List<ReportDTO> reportDTOList = new ArrayList<>();
        for (Report r : quizEntity.getReportList()) {
            ReportDTO dto =
                    ReportDTO.builder()
                            .reportNo(r.getReportNo())
                            .memberName(r.getMember().getMemberName())
                            .reportComment(r.getReportComment())
                            .reportDate(r.getReportDate())
                            .build();
            reportDTOList.add(dto);
        }
        quizDTO.setReportDTOList(reportDTOList);
        List<TestcaseDTO> testcaseDTOList = new ArrayList<>();
        for (Testcase tc : quizEntity.getTestcaseList()) {
            TestcaseDTO dto =
                    TestcaseDTO.builder()
                            .testcaseNo(tc.getTestcaseNo())
                            .testcaseInput(tc.getTestcaseInput())
                            .testcaseOutput(tc.getTestcaseOutput())
                            .build();
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
                        .correctCodeUrl(quizDTO.getCorrectCodeUrl())
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

    /* Delete : 문제 삭제 */
    public void removeQuiz(Long quizNo) throws MyException {
        repository.deleteById(quizNo);
    }
}
