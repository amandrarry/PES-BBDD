package com.pesados.purplepoint.api.exception;

public class UserRegisterBadRequestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserRegisterBadRequestException() {
        super("The information provided for the registration of the user is incorrect.");
    }
}