package com.cgi.sophos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  @Value("${app.version}")
  private String appVersion;

  @Value("${app.name}")
  private String appName;

  @Value("${app.responsible-name}")
  private String responsibleName;

  @Value("${app.responsible-email}")
  private String responsibleEmail;

  @Bean
  public OpenAPI openApi() {
    var license = new License();
    license.setName("All rights reserved - CGI Inc.");

    var contact = new Contact();
    contact.setName(responsibleName);
    contact.setEmail(responsibleEmail);

    return new OpenAPI()
        .components(new Components())
        .info(new Info().title(appName).contact(contact).version(appVersion).license(license));
  }
}
