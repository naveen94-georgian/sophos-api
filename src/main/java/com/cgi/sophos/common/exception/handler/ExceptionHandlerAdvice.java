package com.cgi.sophos.common.exception.handler;

import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;
import static org.zalando.problem.Status.UNAUTHORIZED;

import com.cgi.sophos.common.exception.UnauthorizedException;
import com.cgi.sophos.common.exception.RestException;

import java.net.URI;
import java.util.Locale;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerAdvice implements ProblemHandling {

  private final MessageSource messageSource;

  @Override
  public boolean isCausalChainsEnabled() {
    return false;
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Problem> handleUnauthorized(UnauthorizedException ex, Locale locale) {
    var errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            Problem.builder()
                .withType(URI.create("https://httpstatuses.com/401"))
                .withTitle("Unauthorized")
                .withDetail(messageSource.getMessage(ex.getMessage(), null, locale))
                .withStatus(UNAUTHORIZED)
                .build());
  }

  @ExceptionHandler(RestException.class)
  public ResponseEntity<Problem> handleRestException(RestException ex, Locale locale) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            Problem.builder()
                .withType(URI.create("https://httpstatuses.com/500"))
                .withTitle("Internal Server Error")
                .withDetail(messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale))
                .withStatus(INTERNAL_SERVER_ERROR)
                .build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Problem> handleException(Exception ex, Locale locale) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            Problem.builder()
                .withType(URI.create("https://httpstatuses.com/500"))
                .withTitle("Internal Server Error")
                .withDetail(ex.getLocalizedMessage())
                .withStatus(INTERNAL_SERVER_ERROR)
                .build());
  }
}
