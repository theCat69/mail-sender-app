package fef.vad.www.app.rest.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import fef.vad.www.app.rest.RestIT;
import fef.vad.www.app.rest.dto.ContactFormDto;
import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.service.ContactFormService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
  void sendMail_withValidBoy_shouldMapAndCallService() {
    //given
    var contactFormDto = new ContactFormDto("name", "email@mail.com",
      "message body bllalalal", List.of("file1", "file2"));
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
    verify(contactFormService, times(1)).sendMail(contactFormCaptor.capture());
    assertThat(contactFormCaptor.getValue())
      .usingRecursiveComparison()
      .isEqualTo(contactFormDto);
  }
}