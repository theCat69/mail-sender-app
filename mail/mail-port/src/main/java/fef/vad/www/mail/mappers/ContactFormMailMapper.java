package fef.vad.www.mail.mappers;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.mail.dto.ContactFormMailDto;
import org.mapstruct.Mapper;

@Mapper
public interface ContactFormMailMapper {
  ContactFormMailDto map(ContactForm contactForm);
}
