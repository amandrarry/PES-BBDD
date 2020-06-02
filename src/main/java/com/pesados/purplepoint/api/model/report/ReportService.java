package com.pesados.purplepoint.api.model.report;

import java.util.List;
import java.util.Optional;

public interface ReportService {


	Optional<Report> getReportById(Long id);
	
	List<Report> getAll();
	
	void deleteReportById(Long id);

	Report saveReport(Report newRep);

}
