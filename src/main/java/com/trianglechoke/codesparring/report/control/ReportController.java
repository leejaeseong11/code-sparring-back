package com.trianglechoke.codesparring.report.control;

import com.trianglechoke.codesparring.report.dto.ReportDTO;
import com.trianglechoke.codesparring.report.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired private ReportService reportService;

    @GetMapping("/{reportNo}")
    public ReportDTO find(@PathVariable Long reportNo) {
        return reportService.findReportByReportNo(reportNo);
    }

    @GetMapping("/all")
    public Page<ReportDTO> findAll(Pageable pageable) {
        return reportService.findReportList(pageable);
    }

    @GetMapping("/date")
    public Page<ReportDTO> findByOrderByReportDateDesc(Pageable pageable) {
        return reportService.findReportList(pageable);
    }

    @GetMapping("/commentNull")
    public Page<ReportDTO> findByReportCommentIsNullOrderByReportDateDesc(Pageable pageable) {
        return reportService.findReportList(pageable);
    }

    @PostMapping
    public Long add(@RequestBody ReportDTO reportDTO) {
        return reportService.addReport(reportDTO);
    }

    @PutMapping("/{reportNo}")
    public void updateComment(@PathVariable Long reportNo, String comment) {
        reportService.modifyReportComment(reportNo, comment);
    }
}
