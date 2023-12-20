package fef.vad.www.mail.port;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.mail.dto.ContactFormMailDto;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailPort implements IMailPort {

  private final JavaMailSender emailSender;
  private final ContactFormMailMapper contactFormMailMapper = Mappers.getMapper(ContactFormMailMapper.class);

  public void send(ContactForm contactForm) {

    ContactFormMailDto contactFormMailDto = contactFormMailMapper.map(contactForm);

    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo("vadcard.felix@gmail.com");
      helper.setFrom(contactFormMailDto.email());
      helper.setSubject(String.format("Contact from : %s", contactFormMailDto.name()));
      helper.setText(contactFormMailDto.message());

      for (String fileString : contactFormMailDto.files()) {
        File file = decodeBase64File("", fileString);
        helper.addAttachment(file.getName(), new FileSystemResource(file));
      }

      emailSender.send(message);
      log.info("Email with attachment sent successfully!");
    } catch (MessagingException e) {
      log.error("Error sending email with attachment: " + e.getMessage());
    }
  }

  private File decodeBase64File(String fileName, String base64File) {
    File file = new File(fileName);
    byte[] decodedFile = Base64.getDecoder().decode(base64File);
    try (FileOutputStream stream = new FileOutputStream(file)) {
      stream.write(decodedFile);
    } catch (IOException e) {
      //TODO do something with exception
      throw new RuntimeException(e);
    }
    return file;
  }
}
