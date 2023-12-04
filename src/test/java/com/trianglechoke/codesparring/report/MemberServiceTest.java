package com.trianglechoke.codesparring.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.report.dao.ReportRepository;
import com.trianglechoke.codesparring.report.dto.ReportDTO;
import com.trianglechoke.codesparring.report.entity.Report;
import com.trianglechoke.codesparring.report.service.ReportServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    Report report;
    List<Report> reportList = new ArrayList<>();

    @Mock ReportRepository reportRepository;
    @InjectMocks ReportServiceImpl reportService;

    @BeforeEach
    void init() {
        Member member =
                Member.builder()
                        .memberNo(1L)
                        .memberId("tester")
                        .memberPwd("123123")
                        .memberName("테스터")
                        .memberProfileImg(1)
                        .memberLevel(1L)
                        .memberExp(10)
                        .memberTier("SILVER")
                        .tierPoint(20L)
                        .memberStatus(1)
                        .loseCnt(1L)
                        .winCnt(1L)
                        .drawCnt(1L)
                        .build();
        Quiz quiz =
                Quiz.builder()
                        .quizNo(1L)
                        .member(member)
                        .quizTitle("문제 제목")
                        .quizContent("문제 내용")
                        .quizTier("SILVER")
                        .quizSubmitCnt(0)
                        .quizSuccessCnt(0)
                        .outputType("int")
                        .build();

        Date LocalDate = new Date();
        report =
                Report.builder()
                        .reportNo(1L)
                        .reportDate(LocalDate)
                        .reportType(1)
                        .reportContent("신고 내용")
                        .quiz(quiz)
                        .member(member)
                        .build();

        reportList.add(report);
        reportList.add(
                Report.builder()
                        .reportNo(2L)
                        .reportDate(LocalDate)
                        .reportType(1)
                        .reportContent("신고 내용2")
                        .quiz(quiz)
                        .member(member)
                        .build());

        reportList.add(
                Report.builder()
                        .reportNo(3L)
                        .reportDate(LocalDate)
                        .reportType(1)
                        .reportContent("신고 내용3")
                        .quiz(quiz)
                        .member(member)
                        .build());
    }

    @Test
    @DisplayName("신고내역 상세 조회")
    void findRoom() {
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));
        ReportDTO reportDTO = reportService.findReportByReportNo(1L);
        assertThat(reportDTO.getReportContent()).isEqualTo("신고 내용");
    }

    @Test
    @DisplayName("신고내역 전체 조회")
    void findAllRoom() {
        Page<Report> mockReportPage = new PageImpl(reportList);
        when(reportRepository.findAll(any(Pageable.class))).thenReturn(mockReportPage);
        List<ReportDTO> reportDTOs = reportService.findReportList(Pageable.unpaged());
        assertThat(reportDTOs.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("신고내역 추가")
    void createReport() {
        Date LocalDate = new Date();
        Report testReport =
                Report.builder()
                        .reportNo(10L)
                        .reportDate(LocalDate)
                        .reportType(1)
                        .reportContent("신고 내용10")
                        .quiz(Quiz.builder().build())
                        .member(Member.builder().build())
                        .build();
        when(reportRepository.save(any())).thenReturn(testReport);

        Long addReportNo =
                reportService.addReport(
                        ReportDTO.builder()
                                .quizNo(Quiz.builder().build().getQuizNo())
                                .memberName(Member.builder().build().getMemberName())
                                .reportType(1)
                                .reportDate(LocalDate)
                                .reportContent("신고 내용10")
                                .build());

        assertThat(addReportNo).isEqualTo(10L);
    }

    @Test
    @DisplayName("신고조치내역 추가")
    void addComment() {
        reportService.addReportComment(1L, "추가함. 2023/12/04");
        verify(reportRepository, times(1)).addReportComment(1L, "추가함. 2023/12/04");
    }
}
