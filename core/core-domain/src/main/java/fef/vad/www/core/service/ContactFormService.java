package fef.vad.www.core.service;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.exception.SendMailException;
import fef.vad.www.mail.port.IMailPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContactFormService {

  private final IMailPort mailPort;
  public void send(ContactForm contactForm) throws SendMailException {
    mailPort.send(contactForm);
  }

}
