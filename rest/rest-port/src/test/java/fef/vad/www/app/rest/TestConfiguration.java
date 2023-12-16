package fef.vad.www.app.rest;

import fef.vad.www.core.service.ContactFormService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

  @MockBean
  ContactFormService contactFormService;

}
