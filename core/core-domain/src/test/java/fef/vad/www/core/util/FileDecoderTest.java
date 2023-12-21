package fef.vad.www.core.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileDecoderTest {

  FileDecoder fileDecoder = new FileDecoder();

  @Test
  void decodeBase64File_withValidData_shouldDecodeProperly() {
    //given
    var fileName = "fileName";
    var base64Str = "aGVsbG8=";
    //when
    var resultFile = fileDecoder.decodeBase64File(fileName, base64Str);
    //then
    assertThat(resultFile)
      .hasName("fileName")
      .hasContent("hello");
  }

}