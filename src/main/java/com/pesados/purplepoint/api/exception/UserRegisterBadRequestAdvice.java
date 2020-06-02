package com.pesados.purplepoint.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
class UserRegisterBadRequestAdvice {

    @ResponseBody
    @ExceptionHandler(UserRegisterBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String employeeNotFoundHandler(UserRegisterBadRequestException ex) {
        return ex.getMessage();
    }
}