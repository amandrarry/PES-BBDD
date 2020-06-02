package com.pesados.purplepoint.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class FirebaseTokenBlankAdvice {

	@ResponseBody
	@ExceptionHandler(FirebaseTokenBlankException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	String blankTokenHandler(FirebaseTokenBlankException ex) {
		return ex.getMessage();
	}
}