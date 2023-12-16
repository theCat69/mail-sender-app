package fef.vad.www.core.service;

import fef.vad.www.core.dto.ContactForm;
import fef.vad.www.core.exception.SendMailException;

public class ContactFormService {

  public void sendMail(ContactForm contactForm) throws SendMailException {
    throw new SendMailException();
  }
}
