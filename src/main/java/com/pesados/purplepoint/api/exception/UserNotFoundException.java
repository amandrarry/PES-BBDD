package com.pesados.purplepoint.api.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long id) {
		super("Could not find user with id: " + id);
	}
	public UserNotFoundException(String str) {
		super("Could not find user with email or token: " + str);
	}

}