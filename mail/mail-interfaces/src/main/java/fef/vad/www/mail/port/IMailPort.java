package fef.vad.www.mail.port;

import fef.vad.www.core.dto.ContactForm;

public interface IMailPort {
  void send(ContactForm contactForm);
}
