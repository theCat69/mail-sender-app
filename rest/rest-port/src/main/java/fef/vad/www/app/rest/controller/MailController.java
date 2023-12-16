package fef.vad.www.app.rest.controller;

import fef.vad.www.app.rest.dto.ContactFormDto;
import fef.vad.www.app.rest.dto.ContactFormResponseDto;
import fef.vad.www.app.rest.mappers.ContactFormRestMapper;
import fef.vad.www.core.exception.SendMailException;
import fef.vad.www.core.service.ContactFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
class MailController {

  private final ContactFormService contactFormService;
  private final ContactFormRestMapper contactFormRestMapper = Mappers.getMapper(ContactFormRestMapper.class);

  @PostMapping
  ContactFormResponseDto sendMail(@RequestBody ContactFormDto contactFormDto) throws SendMailException {
    contactFormService.sendMail(contactFormRestMapper.map(contactFormDto));
    return new ContactFormResponseDto("OK");
  }

}
