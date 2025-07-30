package com.seed4j.wire.frontend.infrastructure.primary;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.seed4j.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@AutoConfigureMockMvc
class RedirectionResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @ValueSource(strings = { "/dummy", "/dummy/", "/foo/dummy", "/foo/bar/dummy" })
  @ParameterizedTest
  void shouldGetViewForForwarding(String path) throws Exception {
    mockMvc.perform(get(path)).andExpect(status().isOk()).andExpect(view().name("forward:/"));
  }

  @Test
  void shouldNotGetViewForForwardingForFileWithExtension() throws Exception {
    mockMvc.perform(get("/dummy.html")).andExpect(status().isNotFound());
  }
}
