package fef.vad.www.rest.controller;

import fef.vad.www.core.exception.SendMailException;
import fef.vad.www.core.service.ContactFormService;
import fef.vad.www.rest.dto.ContactFormDto;
import fef.vad.www.rest.dto.ContactFormResponseDto;
import fef.vad.www.rest.mappers.ContactFormRestMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
class MailController {

  private final ContactFormService contactFormService;
  private final ContactFormRestMapper contactFormRestMapper = Mappers.getMapper(ContactFormRestMapper.class);

  @PostMapping
  ContactFormResponseDto send(@RequestBody ContactFormDto contactFormDto) throws SendMailException {
    contactFormService.send(contactFormRestMapper.map(contactFormDto));
    return new ContactFormResponseDto("OK");
  }

}
