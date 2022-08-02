package counter.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import counter.app.controller.CounterController;


@SpringBootTest
class CounterAppTests {

  @Autowired
  private CounterController helloWorldController;

  @Test
  void contextLoads() {
    // to ensure that controller is getting created inside the application context
    assertNotNull(helloWorldController);
  }

}
