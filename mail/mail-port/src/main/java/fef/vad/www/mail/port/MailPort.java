package fef.vad.www.mail.port;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.exception.FileDecodingException;
import fef.vad.www.core.exception.SendMailException;
import fef.vad.www.mail.MailConfigurationProperties;
import fef.vad.www.mail.dto.ContactFormMailDto;
import fef.vad.www.mail.dto.FileMailDto;
import fef.vad.www.mail.mappers.ContactFormMailMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailPort implements IMailPort {

  private final MailConfigurationProperties mailConfigurationProperties;
  private final JavaMailSender emailSender;
  private final ContactFormMailMapper contactFormMailMapper = Mappers.getMapper(ContactFormMailMapper.class);

  public void send(ContactForm contactForm) throws SendMailException {

    ContactFormMailDto contactFormMailDto = contactFormMailMapper.map(contactForm);

    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(mailConfigurationProperties.getTo());
      helper.setFrom(mailConfigurationProperties.getFrom());
      helper.setSubject(String.format("[%s] Contact from %s",
        contactFormMailDto.email(), contactFormMailDto.name()));
      helper.setText(String.format("%s \n <a href=\"mailto:%s\">Respond<a/>",
        contactFormMailDto.message(), contactFormMailDto.email()));

      for (FileMailDto fileMailDto : contactFormMailDto.files()) {
        try (InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(fileMailDto.content()))) {
          helper.addAttachment(fileMailDto.name(), new ByteArrayDataSource(stream, "application/octet-stream"));
        } catch (IOException | IllegalArgumentException e) {
          throw new FileDecodingException(e);
        }
      }

      emailSender.send(message);
      log.info("Email with attachment sent successfully!");
    } catch (MailException | MessagingException | FileDecodingException e) {
      throw new SendMailException(e);
    }
  }

}
