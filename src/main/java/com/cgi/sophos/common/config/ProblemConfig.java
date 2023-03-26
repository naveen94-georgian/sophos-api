package com.cgi.sophos.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
@RequiredArgsConstructor
public class ProblemConfig implements InitializingBean {

  private final ObjectMapper objectMapper;

  @Override
  public void afterPropertiesSet() {
    objectMapper.registerModules(
        new ProblemModule(), new ConstraintViolationProblemModule(), new JavaTimeModule());
  }
}
