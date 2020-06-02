package com.pesados.purplepoint.api.model.report;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

	Optional<Report> findByReportid(Long id);

	void deleteByReportid(Long id);
}
