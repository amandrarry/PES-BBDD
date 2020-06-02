package com.pesados.purplepoint.api.exception;

public class ImageNotFoundException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ImageNotFoundException(Long id) {
		super("Could not find image with id: " + id);
	}

}
