package fef.vad.www.mail;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class TestConfiguration {

  @Autowired
  private JavaMailSenderImpl javaMailSender;

  @PostConstruct
  private void initJavaMailSender() {
    javaMailSender.setPort(AppGreenMailExtension.greenMail.getSmtp().getPort());
  }
}
