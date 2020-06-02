package com.pesados.purplepoint.api.exception;

public class ReportNotFoundException extends RuntimeException{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ReportNotFoundException(Long id) {
		super("Could not find report with id: " + id);
	}
}
