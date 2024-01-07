package fef.vad.www;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AppGreenMailExtension extends GreenMailExtension {
  public static GreenMail greenMail;

  public AppGreenMailExtension() {
    super(ServerSetupTest.SMTP);
    super.withConfiguration(GreenMailConfiguration.aConfig().withUser("user", "admin"));
    super.withPerMethodLifecycle(false);
  }

  @Override
  public void beforeAll(ExtensionContext context) {
    super.beforeAll(context);
    greenMail = getGreenMail();
  }

}