package com.cgi.sophos.exception;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String errorMessage) {
    super(errorMessage);
  }
}
