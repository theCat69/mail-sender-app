package fef.vad.www;

import fef.vad.www.core.service.ContactFormService;
import fef.vad.www.core.util.FileDecoder;
import fef.vad.www.mail.port.MailPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationDomainBeanConfiguration {

  @Bean
  public ContactFormService contactFormService(MailPort mailPort) {
    return new ContactFormService(mailPort);
  }

  @Bean
  public FileDecoder fileDecoder() {
    return new FileDecoder();
  }
}
