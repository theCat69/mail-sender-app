package fef.vad.www.mail.mappers;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.dto.FileDomain;
import fef.vad.www.mail.dto.ContactFormMailDto;
import fef.vad.www.mail.dto.FileMailDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ContactFormMailMapper {
  ContactFormMailDto map(ContactForm contactForm);
  FileMailDto map(FileDomain fileDomain);
  List<FileMailDto> map(List<FileDomain> fileDomains);
}
