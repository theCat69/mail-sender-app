package fef.vad.www.mail.mappers;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.dto.FileDomain;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ContactFormMailMapperTest {

  private ContactFormMailMapper contactFormMailMapper = Mappers.getMapper(ContactFormMailMapper.class);

  @Test
  void mapFromContactFormShouldMapToContactFormMailDto() {
    //given
    var contactForm = new ContactForm("name", "email", "message", List.of(
      new FileDomain("fileName1", "file1"),
      new FileDomain("fileName2", "file2")
    ));
    //when
    var result = contactFormMailMapper.map(contactForm);
    //then
    assertThat(result).usingRecursiveComparison().isEqualTo(contactForm);
  }

  @Test
  void mapFromFileDomainShouldMapToFileMailDto() {
    //given
    var fileDomain = new FileDomain("fileName1", "file1");
    //when
    var result = contactFormMailMapper.map(fileDomain);
    //then
    assertThat(result).usingRecursiveComparison().isEqualTo(fileDomain);
  }

  @Test
  void mapFromListOfFileDomainShouldMapToListOfFileMailDto() {
    //given
    var fileDomain = List.of(
      new FileDomain("fileName1", "file1"),
      new FileDomain("fileName2", "file2")
    );
    //when
    var result = contactFormMailMapper.map(fileDomain);
    //then
    assertThat(result).usingRecursiveComparison().isEqualTo(fileDomain);
  }
}