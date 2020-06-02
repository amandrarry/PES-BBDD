package com.pesados.purplepoint.api.exception;

public class FirebaseTokenBlankException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public FirebaseTokenBlankException() {
		super("Firebase token not found!");
	}
}