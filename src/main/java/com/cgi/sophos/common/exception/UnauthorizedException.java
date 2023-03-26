package com.cgi.sophos.common.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

  private String message;

  public UnauthorizedException(String message) {
    super(message);
    this.message = message;
  }
}
