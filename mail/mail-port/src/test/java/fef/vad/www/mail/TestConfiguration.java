package fef.vad.www.mail;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Slf4j
@Configuration
public class TestConfiguration {

  @Autowired
  private JavaMailSenderImpl javaMailSender;

  @PostConstruct
  private void initJavaMailSender() {
    log.info("port to use : {}", AppGreenMailExtension.greenMail.getSmtp().getPort());
    javaMailSender.setPort(AppGreenMailExtension.greenMail.getSmtp().getPort());
  }
}
