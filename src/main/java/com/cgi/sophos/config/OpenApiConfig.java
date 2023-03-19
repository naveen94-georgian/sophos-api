package com.cgi.sophos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openApi() {
    var license = new License();
    license.setName("All rights reserved - CGI Inc.");

    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("Sophos Absence Planner").license(license));
  }
}
