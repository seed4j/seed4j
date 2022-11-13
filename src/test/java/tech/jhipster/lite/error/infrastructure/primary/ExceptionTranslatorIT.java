package tech.jhipster.lite.error.infrastructure.primary;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;

/**
 * Integration tests {@link ExceptionTranslator} controller advice.
 */
@IntegrationTest
@AutoConfigureMockMvc
class ExceptionTranslatorIT {

  @Autowired
  ExceptionTranslator exceptionTranslator;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldHandleMethodArgumentNotValid() throws Exception {
    mockMvc
      .perform(post("/api/exception-translator-test/method-argument").content("{}").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
  }

  @Test
  void shouldMissingServletRequestPartException() throws Exception {
    mockMvc
      .perform(get("/api/exception-translator-test/missing-servlet-request-part"))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
  }

  @Test
  void shouldHandleMissingServletRequestParameterException() throws Exception {
    mockMvc
      .perform(get("/api/exception-translator-test/missing-servlet-request-parameter"))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
  }

  @Test
  void shouldHandleExceptionWithResponseStatus() throws Exception {
    mockMvc
      .perform(get("/api/exception-translator-test/response-status"))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
      .andExpect(jsonPath("$.title").value("test response status"));
  }

  @Test
  void shouldHandleInternalServerError() throws Exception {
    mockMvc
      .perform(get("/api/exception-translator-test/internal-server-error"))
      .andExpect(status().isInternalServerError())
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
      .andExpect(jsonPath("$.message").value("error.http.500"))
      .andExpect(jsonPath("$.title").value("Internal Server Error"));
  }

  @Test
  void shouldHandleBadRequestError() throws Exception {
    mockMvc
      .perform(get("/api/exception-translator-test/bad-request-error"))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
      .andExpect(jsonPath("$.message").value("error.http.400"))
      .andExpect(jsonPath("$.title").value("Bad request error"));
  }

  @Test
  void shouldHandleBadRequestErrorWithoutDetails() throws Exception {
    mockMvc
      .perform(get("/api/exception-translator-test/bad-request-error"))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
      .andExpect(jsonPath("$.message").value("error.http.400"))
      .andExpect(jsonPath("$.title").value("Bad request error"));
  }

  @Test
  void shouldHandleHttpMessageConversionExceptionWithDetails() throws Exception {
    mockMvc
      .perform(get("/api/exception-translator-test/http-message-conversion-exception"))
      .andExpect(status().isInternalServerError())
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
      .andExpect(jsonPath("$.message").value("error.http.500"))
      .andExpect(jsonPath("$.title").value("Internal Server Error"));
  }

  @Test
  void shouldHandleExceptionContainsPackageName() throws Exception {
    mockMvc
      .perform(get("/api/exception-translator-test/null-pointer-exception"))
      .andExpect(status().isInternalServerError())
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
      .andExpect(jsonPath("$.message").value("error.http.500"))
      .andExpect(jsonPath("$.title").value("Internal Server Error"));
  }

  @Test
  void shouldHandleGeneratorException() throws Exception {
    mockMvc
      .perform(get("/api/exception-translator-test/generator-exception-test"))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
      .andExpect(jsonPath("$.message").value("error.http.400"))
      .andExpect(jsonPath("$.title").value("Test generator exception"));
  }
}
