package com.pesados.purplepoint.api.exception;

import com.pesados.purplepoint.api.exception.UnauthorizedDeviceException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
class UnauthorizedDeviceAdvice {
  @ResponseBody
  @ExceptionHandler(UnauthorizedDeviceException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  String userNotFoundHandler(UnauthorizedDeviceException ex) {
    return ex.getMessage();
  }
}