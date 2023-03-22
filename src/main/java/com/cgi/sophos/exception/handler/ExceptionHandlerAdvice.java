package com.cgi.sophos.exception.handler;

import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;
import static org.zalando.problem.Status.UNAUTHORIZED;

import com.cgi.sophos.exception.ServiceException;
import com.cgi.sophos.exception.UnauthorizedException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class ExceptionHandlerAdvice implements ProblemHandling {
  @Override
  public boolean isCausalChainsEnabled() {
    return false;
  }

  @ExceptionHandler({HttpClientErrorException.Unauthorized.class, UnauthorizedException.class})
  public ResponseEntity<Problem> handleUnauthorized(Exception ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            Problem.builder()
                .withType(URI.create("https://httpstatuses.com/401"))
                .withTitle("Unauthorized")
                .withDetail(ex.getMessage())
                .withStatus(UNAUTHORIZED)
                .build());
  }

  @ExceptionHandler({ServiceException.class})
  public ResponseEntity<Problem> handleServiceException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            Problem.builder()
                .withType(URI.create("https://httpstatuses.com/500"))
                .withTitle("Internal Server Error")
                .withDetail(ex.getMessage())
                .withStatus(INTERNAL_SERVER_ERROR)
                .build());
  }
}
