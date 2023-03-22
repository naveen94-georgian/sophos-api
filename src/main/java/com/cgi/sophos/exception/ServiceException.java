package com.cgi.sophos.exception;

public class ServiceException extends RuntimeException {
  public ServiceException(String errorMessage) {
    super(errorMessage);
  }
}
