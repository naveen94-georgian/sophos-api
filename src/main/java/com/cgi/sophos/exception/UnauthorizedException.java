package com.cgi.sophos.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {
  HttpStatus status = HttpStatus.UNAUTHORIZED;

  public UnauthorizedException(String errorMessage) {
    super(errorMessage);
  }
}
