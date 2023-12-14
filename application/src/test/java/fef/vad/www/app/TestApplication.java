package fef.vad.www.app;

import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log
@SpringBootApplication
public class TestApplication {
  private static int count = 0;

  // Can be used to control number of test context build for test in different modules
  // test context creation are the bottleneck of test performance
  public TestApplication() {
    log.info("Hello from Test Application");
    count++;
    log.info("Count : " + count);
  }
}
