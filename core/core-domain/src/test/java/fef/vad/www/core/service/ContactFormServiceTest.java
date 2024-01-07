package fef.vad.www.core.service;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.dto.FileDomain;
import fef.vad.www.core.exception.SendMailException;
import fef.vad.www.mail.port.IMailPort;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactFormServiceTest {

  @Mock
  IMailPort mailPort;

  @InjectMocks
  ContactFormService contactFormService;

  @Test
  @SneakyThrows
  void send_shouldJustCallSend() {
    //given
    var argCaptor = ArgumentCaptor.forClass(ContactForm.class);
    var contactForm = new ContactForm("name", "email", "message", List.of(
      new FileDomain("fileName1", "file1"),
      new FileDomain("fileName2", "file2")
    ));
    //when
    contactFormService.send(contactForm);
    //then
    verify(mailPort, times(1)).send(argCaptor.capture());
    assertThat(argCaptor.getValue()).isEqualTo(contactForm);
  }

  @Test
  @SneakyThrows
  void send_whenMailPortThrow_shouldRethrow() {
    //given
    var contactForm = new ContactForm("name", "email", "message", List.of(
      new FileDomain("fileName1", "file1"),
      new FileDomain("fileName2", "file2")
    ));
    doThrow(new SendMailException(new Exception("I am the cause"))).when(mailPort).send(contactForm);
    //when & then
    assertThatThrownBy(() -> contactFormService.send(contactForm))
      .isInstanceOf(SendMailException.class);
  }
}