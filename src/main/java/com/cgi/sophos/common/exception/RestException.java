package com.cgi.sophos.common.exception;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {
  private String message;
  private Object[] args;

  public RestException(String message, Object... args) {
    super(message);
    this.message = message;
    this.args = args;
  }
}
