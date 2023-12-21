package fef.vad.www.core.exception;

public class FileDecodingException extends Exception {
  private static final String MESSAGE = "Decoding base64 file content failed";

  public FileDecodingException(Exception cause) {
    super(MESSAGE, cause);
  }
}
