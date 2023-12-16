package fef.vad.www.app.rest;

import fef.vad.www.core.service.ContactFormService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfiguration {

  @Bean
  public ContactFormService contactFormService() {
    return new ContactFormService();
  }
}
