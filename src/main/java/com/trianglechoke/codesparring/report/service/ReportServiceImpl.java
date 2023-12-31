package com.trianglechoke.codesparring.report.service;

import static com.trianglechoke.codesparring.exception.ErrorCode.REPORT_LIST_NOT_FOUND;
import static com.trianglechoke.codesparring.exception.ErrorCode.REPORT_NOT_FOUND;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.util.SecurityUtil;
import com.trianglechoke.codesparring.quiz.dao.QuizRepository;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.report.dao.ReportRepository;
import com.trianglechoke.codesparring.report.dto.ReportDTO;
import com.trianglechoke.codesparring.report.entity.Report;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired private ReportRepository reportRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private QuizRepository quizRepository;

    @Transactional
    public ReportDTO findReportByReportNo(Long reportNo) {
        Optional<Report> optReport = reportRepository.findById(reportNo);
        if (optReport.isEmpty()) {
            throw new MyException(REPORT_NOT_FOUND);
        }
        Report reportEntity = optReport.get();

        return ReportDTO.builder()
                .reportNo(reportEntity.getReportNo())
                .reportDate(reportEntity.getReportDate())
                .reportType(reportEntity.getReportType())
                .reportContent(reportEntity.getReportContent())
                .quizNo(reportEntity.getQuiz().getQuizNo())
                .memberName(reportEntity.getMember().getMemberName())
                .reportComment(reportEntity.getReportComment())
                .build();
    }

    @Transactional
    public Page<ReportDTO> findReportList(Pageable pageable) {
        List<ReportDTO> selectedReportList = new ArrayList<>();
        Page<Report> reportList;
        reportList = reportRepository.findAll(pageable);
        if (reportList.isEmpty()) {
            throw new MyException(REPORT_LIST_NOT_FOUND);
        }
        for (Report report : reportList) {
            selectedReportList.add(
                    ReportDTO.builder()
                            .reportNo(report.getReportNo())
                            .quizNo(report.getQuiz().getQuizNo())
                            .memberName(report.getMember().getMemberName())
                            .reportDate(report.getReportDate())
                            .reportType(report.getReportType())
                            .reportContent(report.getReportContent())
                            .build());
        }
        return new PageImpl<>(selectedReportList, pageable, reportList.getTotalElements());
    }

    @Transactional
    public Long addReport(ReportDTO reportDTO) {
        Optional<Member> currentMember =
                memberRepository.findByMemberName(SecurityUtil.getCurrentMemberName());
        Member m = currentMember.get();
        Optional<Quiz> selectedQuiz = quizRepository.findById(reportDTO.getQuizNo());
        Quiz q = selectedQuiz.get();

        return reportRepository
                .save(
                        Report.builder()
                                .quiz(q)
                                .member(m)
                                .reportType(reportDTO.getReportType())
                                .reportDate(reportDTO.getReportDate())
                                .reportContent(reportDTO.getReportContent())
                                .build())
                .getReportNo();
    }

    @Transactional
    public void modifyReportComment(Long reportNo, String reportComment) {
        reportRepository.updateReportComment(reportNo, reportComment);
    }

    @Transactional
    public Page<ReportDTO> findAllByOrderByReportDateDesc(Pageable pageable) {
        List<ReportDTO> selectedReportList = new ArrayList<>();
        Page<Report> reportList;
        reportList = reportRepository.findAllByOrderByReportDateDesc(pageable);
        if (reportList.isEmpty()) {
            throw new MyException(REPORT_LIST_NOT_FOUND);
        }
        for (Report report : reportList) {
            selectedReportList.add(
                    ReportDTO.builder()
                            .reportNo(report.getReportNo())
                            .quizNo(report.getQuiz().getQuizNo())
                            .memberName(report.getMember().getMemberName())
                            .reportDate(report.getReportDate())
                            .reportType(report.getReportType())
                            .reportContent(report.getReportContent())
                            .build());
        }
        return new PageImpl<>(selectedReportList, pageable, reportList.getTotalElements());
    }

    @Transactional
    public Page<ReportDTO> findAllByReportCommentIsNullOrderByReportDateDesc(Pageable pageable) {
        List<ReportDTO> selectedReportList = new ArrayList<>();
        Page<Report> reportList;
        reportList = reportRepository.findAllByReportCommentIsNullOrderByReportDateDesc(pageable);
        if (reportList.isEmpty()) {
            throw new MyException(REPORT_LIST_NOT_FOUND);
        }
        for (Report report : reportList) {
            selectedReportList.add(
                    ReportDTO.builder()
                            .reportNo(report.getReportNo())
                            .quizNo(report.getQuiz().getQuizNo())
                            .memberName(report.getMember().getMemberName())
                            .reportDate(report.getReportDate())
                            .reportType(report.getReportType())
                            .reportContent(report.getReportContent())
                            .build());
        }
        return new PageImpl<>(selectedReportList, pageable, reportList.getTotalElements());
    }

    @Transactional
    public void removeReport(Long reportNo) {
        reportRepository.deleteById(reportNo);
    }
}
