package com.seed4j.shared.error.infrastructure.primary;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seed4j.IntegrationTest;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@AutoConfigureMockMvc
class AssertionErrorsHandlerIT {

  @Autowired
  private MockMvc rest;

  @Test
  void shouldHandleMissingMandatoryValueFromPrimary() throws Exception {
    rest
      .perform(get("/api/assertion-errors/missing-primary-mandatory-value"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("Missing mandatory value"))
      .andExpect(jsonPath("detail").value("Content of myField must be set"))
      .andExpect(jsonPath("key").value("MISSING_MANDATORY_VALUE"));
  }

  @Test
  void shouldHandleMissingMandatoryValueFromDomain() throws Exception {
    rest
      .perform(get("/api/assertion-errors/missing-domain-mandatory-value"))
      .andExpect(status().isInternalServerError())
      .andExpect(jsonPath("title").value("Missing mandatory value"))
      .andExpect(jsonPath("detail").value("Content of myField must be set"))
      .andExpect(jsonPath("key").value("MISSING_MANDATORY_VALUE"));
  }

  @Test
  void shouldHandleMissingMandatoryValueInFrench() throws Exception {
    rest
      .perform(get("/api/assertion-errors/missing-primary-mandatory-value").locale(Locale.FRANCE))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("Valeur obligatoire manquante"))
      .andExpect(jsonPath("detail").value("Le contenu de myField doit être renseigné"))
      .andExpect(jsonPath("key").value("MISSING_MANDATORY_VALUE"));
  }

  @Test
  void shouldHandleNumberValueTooLow() throws Exception {
    rest
      .perform(get("/api/assertion-errors/number-value-too-low"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("Number value too low"))
      .andExpect(jsonPath("detail").value("Value of myField must be over 1337 (was 42)"))
      .andExpect(jsonPath("key").value("NUMBER_VALUE_TOO_LOW"));
  }

  @Test
  void shouldHandleNumberValueTooHigh() throws Exception {
    rest
      .perform(get("/api/assertion-errors/number-value-too-high"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("Number value too high"))
      .andExpect(jsonPath("detail").value("Value of myField must be under 42 (was 1337)"))
      .andExpect(jsonPath("key").value("NUMBER_VALUE_TOO_HIGH"));
  }

  @Test
  void shouldHandleStringTooLong() throws Exception {
    rest
      .perform(get("/api/assertion-errors/string-too-long"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("String too long"))
      .andExpect(jsonPath("detail").value("Content of myField is too long, must be less than 10 character(s) (was 42)"))
      .andExpect(jsonPath("key").value("STRING_TOO_LONG"));
  }

  @Test
  void shouldHandleStringTooShort() throws Exception {
    rest
      .perform(get("/api/assertion-errors/string-too-short"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("String too short"))
      .andExpect(jsonPath("detail").value("Content of myField is too short, must be over 42 character(s) (was 10)"))
      .andExpect(jsonPath("key").value("STRING_TOO_SHORT"));
  }

  @Test
  void shouldHandleTooManyElements() throws Exception {
    rest
      .perform(get("/api/assertion-errors/too-many-elements"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("Too many elements"))
      .andExpect(jsonPath("detail").value("There are too many elements in myField, max is 1 (current 2 element(s))"))
      .andExpect(jsonPath("key").value("TOO_MANY_ELEMENTS"));
  }

  @Test
  void shouldHandleNullElementInCollection() throws Exception {
    rest
      .perform(get("/api/assertion-errors/null-element-in-collection"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("Null element in collection"))
      .andExpect(jsonPath("detail").value("There is a null element in myField"))
      .andExpect(jsonPath("key").value("NULL_ELEMENT_IN_COLLECTION"));
  }

  @Test
  void shouldHandleStringWithWhitespaces() throws Exception {
    rest
      .perform(get("/api/assertion-errors/string-with-whitespaces"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("String with whitespaces"))
      .andExpect(jsonPath("detail").value("The string myField must not have any whitespaces"))
      .andExpect(jsonPath("key").value("STRING_WITH_WHITESPACES"));
  }

  @Test
  void shouldHandleStringNotMatchingExpectedPattern() throws Exception {
    rest
      .perform(get("/api/assertion-errors/string-not-matching-pattern"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("String not matching the expected pattern"))
      .andExpect(jsonPath("detail").value("The string myField must match the pattern ^[a-z0-9-]+$"))
      .andExpect(jsonPath("key").value("STRING_NOT_MATCHING_PATTERN"));
  }

  @Test
  void shouldHandleNotAfterTime() throws Exception {
    rest
      .perform(get("/api/assertion-errors/not-after-time"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("Too early date"))
      .andExpect(jsonPath("detail").value("Date in myField can't be that early"))
      .andExpect(jsonPath("key").value("NOT_AFTER_TIME"));
  }

  @Test
  void shouldHandleNotBeforeTime() throws Exception {
    rest
      .perform(get("/api/assertion-errors/not-before-time"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("title").value("Too late date"))
      .andExpect(jsonPath("detail").value("Date in myField can't be that late"))
      .andExpect(jsonPath("key").value("NOT_BEFORE_TIME"));
  }
}
