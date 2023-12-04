package com.trianglechoke.codesparring.report.dao;

import com.trianglechoke.codesparring.report.entity.Report;
import com.trianglechoke.codesparring.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findAll(Pageable pageable);

    @Modifying
    @Query(
            value = "UPDATE report\n" +
                    "SET report_comment = :comment\n" +
                    "WHERE report_no = :reportNo",
            nativeQuery = true)
    void addReportComment(@Param("reportNo") Long reportNo, @Param("comment") String comment);
}
