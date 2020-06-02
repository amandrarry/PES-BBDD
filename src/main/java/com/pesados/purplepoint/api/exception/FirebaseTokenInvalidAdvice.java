package com.pesados.purplepoint.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class FirebaseTokenInvalidAdvice {

	@ResponseBody
	@ExceptionHandler(FirebaseTokenInvalidException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	String badTokenHandler(FirebaseTokenInvalidException ex) {
		return ex.getMessage();
	}
}