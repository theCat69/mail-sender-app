package fef.vad.www.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.exception.SendMailException;
import fef.vad.www.core.service.ContactFormService;
import fef.vad.www.rest.RestIT;
import fef.vad.www.rest.dto.ContactFormDto;
import fef.vad.www.rest.dto.FileDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RestIT
class MailControllerIT {

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  ContactFormService contactFormService;

  @Test
  @SneakyThrows
  void send_withValidBody_shouldMapAndCallService() {
    //given
    var contactFormDto = getContactFormDto();
    var contactFormDtoJson = objectMapper.writeValueAsString(contactFormDto);
    var contactFormCaptor = ArgumentCaptor.forClass(ContactForm.class);
    //when
    this.mockMvc.perform(
        post("/mail")
          .contentType(MediaType.APPLICATION_JSON)
          .content(contactFormDtoJson)
      ).andDo(print())
      .andExpect(status().isOk());
    //then
    verify(contactFormService, times(1)).send(contactFormCaptor.capture());
    assertThat(contactFormCaptor.getValue())
      .usingRecursiveComparison()
      .isEqualTo(contactFormDto);
  }

  @Test
  @SneakyThrows
  void send_withInValidUrl_shouldFailWith4xx() {
    //given
    var contactFormDto = getContactFormDto();
    var contactFormDtoJson = objectMapper.writeValueAsString(contactFormDto);
    //when & then
    this.mockMvc.perform(
        post("/mail_wrong_url")
          .contentType(MediaType.APPLICATION_JSON)
          .content(contactFormDtoJson)
      ).andDo(print())
      .andExpect(status().is4xxClientError());
  }

  @Test
  @SneakyThrows
  void send_sendMailExceptionIsThrown_shouldFailWith5xx() {
    //given
    var contactFormDto = getContactFormDto();
    var contactFormDtoJson = objectMapper.writeValueAsString(contactFormDto);
    doThrow(new SendMailException(new Exception("I am the cause"))).when(contactFormService).send(any());
    //when & then
    this.mockMvc.perform(
        post("/mail")
          .contentType(MediaType.APPLICATION_JSON)
          .content(contactFormDtoJson)
      ).andDo(print())
      .andExpect(status().is5xxServerError());
  }

  private static ContactFormDto getContactFormDto() {
    return new ContactFormDto("name", "email@mail.com",
      "message body bllalalal", List.of(
      new FileDto("fileName1", "file1"),
      new FileDto("fileName2", "file2")
    ));
  }
}