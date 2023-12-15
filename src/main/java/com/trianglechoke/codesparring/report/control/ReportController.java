package com.trianglechoke.codesparring.report.control;

import com.trianglechoke.codesparring.report.dto.ReportDTO;
import com.trianglechoke.codesparring.report.service.ReportService;

import com.trianglechoke.codesparring.report.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportController {
    @Autowired private ReportServiceImpl reportServiceImpl;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/report/{reportNo}")
    public ReportDTO findReportByReportNo(@PathVariable Long reportNo) {
        return reportServiceImpl.findReportByReportNo(reportNo);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //    @GetMapping("/admin/report/all")
    //    public Page<ReportDTO> findAll(
    //            @PageableDefault(size = 10, sort = "reportDate", direction = Sort.Direction.DESC)
    //                    Pageable pageable) {
    //        return reportService.findReportList(pageable);
    //    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/report/all")
    public Page<ReportDTO> findAllByOrderByReportDateDesc(
            @PageableDefault(size = 10) Pageable pageable) {
        return reportServiceImpl.findAllByOrderByReportDateDesc(pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/report/commentNull")
    public Page<ReportDTO> findAllByReportCommentIsNullOrderByReportDateDesc(
            @PageableDefault(size = 10, sort = "reportDate", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return reportServiceImpl.findAllByReportCommentIsNullOrderByReportDateDesc(pageable);
    }

    @PostMapping("/report")
    public Long add(@RequestBody ReportDTO reportDTO) {
        return reportServiceImpl.addReport(reportDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/report/{reportNo}")
    public void updateComment(@PathVariable Long reportNo, String comment) {
        reportServiceImpl.modifyReportComment(reportNo, comment);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/report/{reportNo}")
    public void remove(@PathVariable Long reportNo) {
        reportServiceImpl.removeReport(reportNo);
    }
}