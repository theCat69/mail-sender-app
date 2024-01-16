package fef.vad.www;

import com.fasterxml.jackson.databind.ObjectMapper;
import fef.vad.www.rest.dto.ContactFormDto;
import fef.vad.www.rest.dto.FileDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AppIT
public class SendMailFullIT {

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  JavaMailSenderImpl javaMailSender;

  @BeforeEach
  void setUp() {
    javaMailSender.setPort(AppGreenMailExtension.greenMail.getSmtp().getPort());
  }

  @Test
  @SneakyThrows
  void send_withValidBoy_shouldSendMail() {
    //given
    var contactFormDto = getContactFormDto();
    var contactFormDtoJson = objectMapper.writeValueAsString(contactFormDto);
    //when
    this.mockMvc.perform(
        post("/mail")
          .contentType(MediaType.APPLICATION_JSON)
          .content(contactFormDtoJson)
      ).andDo(print())
      .andExpect(status().isOk());
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

  private static ContactFormDto getContactFormDto() {
    return new ContactFormDto("name", "email@mail.com", "message", List.of(
      new FileDto("fileName1.txt", getContentAsB64("file1")),
      new FileDto("fileName2.txt", getContentAsB64("file2"))
    ));
  }

  private static String getContentAsB64(String name) {
    return Base64.getEncoder().encodeToString(name.getBytes(StandardCharsets.UTF_8));
  }
}
