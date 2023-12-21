package fef.vad.www.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FileDecoder {

   public File decodeBase64File(String fileName, String base64File) {
    File file = new File(fileName);
    byte[] decodedFile = Base64.getDecoder().decode(base64File);
    try (FileOutputStream stream = new FileOutputStream(file)) {
      stream.write(decodedFile);
    } catch (IOException e) {
      //TODO send core exception
      throw new RuntimeException(e);
    }
    return file;
  }
}
