package fef.vad.www.mail.port;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.dto.FileDomain;
import fef.vad.www.mail.MailIT;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@MailIT
class MailPortIT {

  @Autowired
  MailPort mailPort;

  @RegisterExtension
  static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
    .withConfiguration(GreenMailConfiguration.aConfig().withUser("user", "admin"))
    .withPerMethodLifecycle(false);

  @Test
  @SneakyThrows
  void send_withValidData_shouldSendMail() {
    //given
    var contactForm = new ContactForm("name", "email", "message", List.of(
      new FileDomain("fileName1.txt", getContentAsB64("file1")),
      new FileDomain("fileName2.txt", getContentAsB64("file2"))
    ));
    //when
    mailPort.send(contactForm);
    //then
    Awaitility.await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
      MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];

      assertThat(receivedMessage.getAllRecipients()).hasSize(1);
      assertThat(receivedMessage.getAllRecipients()[0]).asString().isEqualTo("vadcard.felix@gmail.com");
      assertThat(receivedMessage.getFrom()[0]).asString().isEqualTo("email");
      assertThat(receivedMessage.getSubject()).asString().contains("name");
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