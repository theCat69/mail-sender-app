package fef.vad.www.mail;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AppGreenMailExtension extends GreenMailExtension {
  public static GreenMail greenMail;

  public AppGreenMailExtension() {
    super(new ServerSetup(0, null, ServerSetup.PROTOCOL_SMTP));
    super.withConfiguration(GreenMailConfiguration.aConfig().withUser("user", "admin"));
    super.withPerMethodLifecycle(false);
  }

  @Override
  public void beforeAll(ExtensionContext context) {
    super.beforeAll(context);
    greenMail = getGreenMail();
  }

}