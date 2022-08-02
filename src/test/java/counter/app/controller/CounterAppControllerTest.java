package counter.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CounterAppControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnExpectedMessage() throws Exception {

    mockMvc.perform(get("/start")).andExpect(status().isOk())
        .andExpect(content().string("task started"));
  }
}
