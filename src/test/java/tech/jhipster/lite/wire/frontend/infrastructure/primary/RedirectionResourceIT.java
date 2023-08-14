package tech.jhipster.lite.wire.frontend.infrastructure.primary;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;

@IntegrationTest
@AutoConfigureMockMvc
class RedirectionResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldGetViewForForwarding() throws Exception {
    mockMvc.perform(get("/dummy")).andExpect(status().isOk()).andExpect(view().name("forward:/"));
  }
}
