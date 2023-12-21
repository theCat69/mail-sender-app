package fef.vad.www.mail.port;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.exception.SendMailException;

public interface IMailPort {
  void send(ContactForm contactForm) throws SendMailException;
}
