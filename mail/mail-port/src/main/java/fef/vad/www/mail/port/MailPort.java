package fef.vad.www.mail.port;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.util.FileDecoder;
import fef.vad.www.mail.dto.ContactFormMailDto;
import fef.vad.www.mail.dto.FileMailDto;
import fef.vad.www.mail.mappers.ContactFormMailMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailPort implements IMailPort {

  private final JavaMailSender emailSender;
  private final FileDecoder fileDecoder;
  private final ContactFormMailMapper contactFormMailMapper = Mappers.getMapper(ContactFormMailMapper.class);

  public void send(ContactForm contactForm) {

    ContactFormMailDto contactFormMailDto = contactFormMailMapper.map(contactForm);

    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      //TODO use a property
      helper.setTo("vadcard.felix@gmail.com");
      helper.setFrom(contactFormMailDto.email());
      helper.setSubject(String.format("Contact from : %s", contactFormMailDto.name()));
      helper.setText(contactFormMailDto.message());

      for (FileMailDto fileMailDto : contactFormMailDto.files()) {
        File file = fileDecoder.decodeBase64File(fileMailDto.name(), fileMailDto.content());
        helper.addAttachment(file.getName(), new FileSystemResource(file));
      }

      emailSender.send(message);
      log.info("Email with attachment sent successfully!");
    } catch (MessagingException e) {
      //TODO send core exception
      log.error("Error sending email with attachment: " + e.getMessage());
    }
  }

}
