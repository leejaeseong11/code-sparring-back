package com.trianglechoke.codesparring.report.service;

import com.trianglechoke.codesparring.report.dto.ReportDTO;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {
    /* 문제 신고 상세 조회 */
    ReportDTO findReportByReportNo(Long reportNo);

    /* 문제 신고 목록 조회 */
    List<ReportDTO> findReportList(Pageable pageable);

    /* 문제 신고 생성 */
    Long addReport(ReportDTO reportDTO);

    /* 신고 조치 내용 생성 */
    void addReportComment(Long reportNo, String comment);
}
