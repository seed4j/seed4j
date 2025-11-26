package com.seed4j.shared.error.infrastructure.primary;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seed4j.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@AutoConfigureMockMvc
class BeanValidationErrorsHandlerIT {

  @Autowired
  private MockMvc rest;

  @Test
  void shouldHandleBodyParameterValidationError() throws Exception {
    rest
      .perform(post("/api/bean-validation-errors/mandatory-body-parameter").content("{}").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is4xxClientError())
      .andExpect(jsonPath("$.title").value("Bean validation error"))
      .andExpect(jsonPath("$.detail").value("One or more fields were invalid. See 'errors' for details."))
      .andExpect(jsonPath("$.errors.parameter").value("must not be blank"));
  }

  @Test
  void shouldHandleControllerParameterValidationError() throws Exception {
    rest
      .perform(get("/api/bean-validation-errors/complicated-path-variable/dummy"))
      .andExpect(status().is4xxClientError())
      .andExpect(jsonPath("$.title").value("Bean validation error"))
      .andExpect(jsonPath("$.detail").value("One or more fields were invalid. See 'errors' for details."))
      .andExpect(jsonPath("$.errors.complicated").value("must match \"complicated\""));
  }
}
