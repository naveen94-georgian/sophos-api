package com.cgi.sophos;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class SophosApplicationTests {

  @Test
  void contextLoads() {
    System.out.println("Unit Test");
    Assertions.assertThat(true).isTrue();
  }
}
