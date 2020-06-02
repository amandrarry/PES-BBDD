package com.pesados.purplepoint.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class WrongPasswordAdvice {
	  @ResponseBody
	  @ExceptionHandler(WrongPasswordException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  String employeeNotFoundHandler(UserNotFoundException ex) {
	    return ex.getMessage();
	  }
}
