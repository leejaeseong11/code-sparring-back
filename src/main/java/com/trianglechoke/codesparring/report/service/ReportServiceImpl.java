package com.trianglechoke.codesparring.report.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.report.dao.ReportRepository;
import com.trianglechoke.codesparring.report.dto.ReportDTO;
import com.trianglechoke.codesparring.report.entity.Report;
import com.trianglechoke.codesparring.room.dao.RoomRepository;
import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.entity.Room;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.trianglechoke.codesparring.exception.ErrorCode.ROOM_NOT_FOUND;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

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
    public List<ReportDTO> findReportList(Pageable pageable) {
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
        return selectedReportList;
    }

    @Transactional
    public Long addReport(ReportDTO reportDTO) {
        Member m = Member.builder().memberName(reportDTO.getMemberName()).build();
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

    @Override
    public void addReportComment(Long reportNo, String comment) {
        reportRepository.addReportComment(reportNo, comment);
    }

}