package fef.vad.www.rest.mappers;

import fef.vad.www.rest.dto.ContactFormDto;
import fef.vad.www.core.dto.ContactForm;
import org.mapstruct.Mapper;

@Mapper
public interface ContactFormRestMapper {
  ContactForm map(ContactFormDto contactFormDto);
}
