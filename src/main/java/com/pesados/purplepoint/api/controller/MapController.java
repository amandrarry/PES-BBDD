package com.pesados.purplepoint.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pesados.purplepoint.api.exception.ReportNotFoundException;
import com.pesados.purplepoint.api.model.report.Report;
import com.pesados.purplepoint.api.model.report.ReportService;
import com.pesados.purplepoint.api.model.user.User;
import com.pesados.purplepoint.api.model.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1")
public class MapController {
	@Autowired
	private ReportService reportService;
	@Autowired
	private UserService userService;

	// Visibilidad User
	@Operation(summary = "Add a new report",
			description = "Adds a new report to the database with the information provided. "
			, tags = { "reports" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Report created",
					content = @Content(schema = @Schema(implementation = Report.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "409", description = "Report already exists") })
	@PostMapping(value = "/map", consumes = { "application/json", "application/xml" })
	Report newReport(
			@Parameter(description="Report to add. Cannot null or empty.",
					required=true, schema=@Schema(implementation = Report.class))
			@Valid @RequestBody Report newRep
	) {
		return this.reportService.saveReport(newRep);
	} 
		
	// Visibilidad User
	@Operation(summary = "Get All Reports", description = "Get ", tags = {"reports"})
	@ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation",
	                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = Report.class)))) })
	@GetMapping(value = "/map", produces = { "application/json", "application/xml"})
	List<Report> all() {
		return reportService.getAll();
	}
	
	@Operation(summary = "Get a report", description = "Get ", tags = {"reports"})
	@ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation",
	                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = Report.class)))) })
	@GetMapping(value = "/map/{id}", produces = { "application/json", "application/xml"})
	Report getOne(
			@Parameter(description="id of the report.", required = true) @PathVariable long id) {
		return reportService.getReportById(id).orElseThrow(() -> new ReportNotFoundException(id));
	}
	
	// Visibilidad User
	@Operation(summary = "Delete a Report", description = "Delete ", tags = {"reports"})
	@ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation",
	                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = Report.class)))) })
	@DeleteMapping(value = "/map/{id}", produces = { "application/json", "application/xml"})
	void delOne(
			@Parameter(description="id of the report.", required = true) @PathVariable long id) {
		reportService.deleteReportById(id);
	}
}
