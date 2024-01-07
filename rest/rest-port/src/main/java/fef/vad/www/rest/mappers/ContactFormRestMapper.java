package fef.vad.www.rest.mappers;

import fef.vad.www.core.dto.FileDomain;
import fef.vad.www.rest.dto.ContactFormDto;
import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.rest.dto.FileDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ContactFormRestMapper {
  ContactForm map(ContactFormDto contactFormDto);
  FileDomain map(FileDto fileDto);
  List<FileDomain> map(List<FileDto> fileDtos);
}
