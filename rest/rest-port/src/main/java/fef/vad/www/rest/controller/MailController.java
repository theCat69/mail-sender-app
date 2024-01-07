package fef.vad.www.rest.controller;

import fef.vad.www.rest.dto.ContactFormDto;
import fef.vad.www.rest.dto.ContactFormResponseDto;
import fef.vad.www.rest.mappers.ContactFormRestMapper;
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
  ContactFormResponseDto send(@RequestBody ContactFormDto contactFormDto) throws SendMailException {
    contactFormService.send(contactFormRestMapper.map(contactFormDto));
    return new ContactFormResponseDto("OK");
  }

}
