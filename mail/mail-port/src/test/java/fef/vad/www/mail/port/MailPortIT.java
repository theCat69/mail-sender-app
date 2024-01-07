package fef.vad.www.mail.port;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.dto.FileDomain;
import fef.vad.www.mail.AppGreenMailExtension;
import fef.vad.www.mail.MailIT;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@MailIT
class MailPortIT {

  @Autowired
  MailPort mailPort;

  @Test
  @SneakyThrows
  void send_withValidData_shouldSendMail() {
    //given
    var contactForm = new ContactForm("name", "email@mail.com", "message", List.of(
      new FileDomain("fileName1.txt", getContentAsB64("file1")),
      new FileDomain("fileName2.txt", getContentAsB64("file2"))
    ));
    //when
    mailPort.send(contactForm);
    //then
    Awaitility.await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
      MimeMessage receivedMessage = AppGreenMailExtension.greenMail.getReceivedMessages()[0];

      assertThat(receivedMessage.getAllRecipients()).hasSize(1);
      assertThat(receivedMessage.getAllRecipients()[0]).asString().isEqualTo("test-to@mail.com");
      assertThat(receivedMessage.getFrom()[0]).asString().isEqualTo("mail-from@mail.com");
      assertThat(receivedMessage.getSubject()).asString().contains("name", "email@mail.com");
      assertThat(receivedMessage.getContent()).isInstanceOf(MimeMultipart.class);
      try {
        MimeMultipart mimeMultipart = (MimeMultipart) receivedMessage.getContent();
        assertThat(mimeMultipart.getCount()).isEqualTo(3);
        assertThat(mimeMultipart.getBodyPart(0).getInputStream())
          .asString(StandardCharsets.UTF_8).contains("message");
        assertThat(mimeMultipart.getBodyPart(1).getInputStream())
          .asString(StandardCharsets.UTF_8).contains("file1");
        assertThat(mimeMultipart.getBodyPart(1).getFileName())
          .isEqualTo("fileName1.txt");
        assertThat(mimeMultipart.getBodyPart(2).getInputStream())
          .asString(StandardCharsets.UTF_8).contains("file2");
        assertThat(mimeMultipart.getBodyPart(2).getFileName())
          .isEqualTo("fileName2.txt");
      } catch (MessagingException e) {
        // do nothing
      }
    });
  }

  private static String getContentAsB64(String name) {
    return Base64.getEncoder().encodeToString(name.getBytes(StandardCharsets.UTF_8));
  }

}