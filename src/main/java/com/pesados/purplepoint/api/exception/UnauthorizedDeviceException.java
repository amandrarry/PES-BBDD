package com.pesados.purplepoint.api.exception;

public class UnauthorizedDeviceException extends RuntimeException {
/**
	 *
	 */
    private static final long serialVersionUID = 1L;
    
    public UnauthorizedDeviceException() {
        super("You don't have permission to acces this resource!");
    }
}
