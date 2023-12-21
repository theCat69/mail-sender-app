package fef.vad.www.core.exception;

public class SendMailException extends Exception {
  private static final String MESSAGE = "Sending mail failed";

  public SendMailException(Exception cause) {
    super(MESSAGE, cause);
  }
}
