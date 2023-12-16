package fef.vad.www.app.rest.mappers;

import fef.vad.www.app.rest.dto.ContactFormDto;
import fef.vad.www.core.dto.ContactForm;
import org.mapstruct.Mapper;

@Mapper
public interface ContactFormRestMapper {
  ContactForm map(ContactFormDto contactFormDto);
}
