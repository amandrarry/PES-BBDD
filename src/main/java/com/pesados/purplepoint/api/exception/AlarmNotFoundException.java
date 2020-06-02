package com.pesados.purplepoint.api.exception;

public class AlarmNotFoundException extends RuntimeException {

	/**
	 *
	 */

	private static final long serialVersionUID = 1L;

	public AlarmNotFoundException(Long id) {
		super("Could not find alarm with id: " + id);
	}

	public AlarmNotFoundException(String str) {
		super("Could not find alarm whose username is: " + str);
	}

}