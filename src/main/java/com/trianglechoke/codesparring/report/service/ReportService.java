package com.trianglechoke.codesparring.report.service;

import com.trianglechoke.codesparring.report.dto.ReportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {
    /* 문제 신고 상세 조회 */
    ReportDTO findReportByReportNo(Long reportNo);

    /* 문제 신고 목록 전체 조회 */
    Page<ReportDTO> findReportList(Pageable pageable);

    /* 문제 신고 목록 최신 날짜순 조회 */
    Page<ReportDTO> findByOrderByReportDateDesc(Pageable pageable);

    /* 문제 신고 최신 날짜순, 조치 내역 없는 목록 조회 */
    Page<ReportDTO> findByReportCommentIsNullOrderByReportDateDesc(Pageable pageable);

    /* 문제 신고 생성 */
    Long addReport(ReportDTO reportDTO);

    /* 신고 조치 내용 생성 */
    void modifyReportComment(Long reportNo, String comment);
}
