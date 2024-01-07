package fef.vad.www;

import fef.vad.www.core.service.ContactFormService;
import fef.vad.www.mail.port.IMailPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationDomainBeanConfiguration {

  @Bean
  public ContactFormService contactFormService(IMailPort mailPort) {
    return new ContactFormService(mailPort);
  }

}
