package com.trianglechoke.codesparring.report.service;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.util.SecurityUtil;
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

    @Transactional
    public ReportDTO findReportByReportNo(Long reportNo) {
        Optional<Report> OptReport = reportRepository.findById(reportNo);
        Report reportEntity = OptReport.get();
        return ReportDTO.builder()
                .reportNo(reportEntity.getReportNo())
                .reportDate(reportEntity.getReportDate())
                .reportType(reportEntity.getReportType())
                .reportContent(reportEntity.getReportContent())
                .quizNo(reportEntity.getQuiz().getQuizNo())
                .memberName(reportEntity.getMember().getMemberName())
                .build();
    }

    @Transactional
    public Page<ReportDTO> findReportList(Pageable pageable) {
        List<ReportDTO> selectedReportList = new ArrayList<>();
        Page<Report> reportList;
        reportList = reportRepository.findAll(pageable);
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

//    @Transactional
//    public Long addReport(ReportDTO reportDTO) {
//        Member m = Member.builder().memberName(reportDTO.getMemberName()).build();
//        Quiz q = Quiz.builder().quizNo(reportDTO.getQuizNo()).build();
//        return reportRepository
//                .save(
//                        Report.builder()
//                                .quiz(q)
//                                .member(m)
//                                .reportType(reportDTO.getReportType())
//                                .reportDate(reportDTO.getReportDate())
//                                .reportContent(reportDTO.getReportContent())
//                                .build())
//                .getReportNo();
//    }

    @Transactional
    public Long addReport(ReportDTO reportDTO) {
        Member m = Member.builder().memberName(SecurityUtil.getCurrentMemberName()).build();
        Quiz q = Quiz.builder().quizNo(reportDTO.getQuizNo()).build();
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
    public void modifyReportComment(Long reportNo, String comment) {
        reportRepository.updateReportComment(reportNo, comment);
    }

    @Transactional
    public Page<ReportDTO> findAllByOrderByReportDateDesc(Pageable pageable) {
        List<ReportDTO> selectedReportList = new ArrayList<>();
        Page<Report> reportList;
        reportList = reportRepository.findAllByOrderByReportDateDesc(pageable);
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
