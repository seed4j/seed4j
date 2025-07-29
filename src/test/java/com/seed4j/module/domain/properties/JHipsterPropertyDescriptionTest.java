package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterPropertyDescriptionTest {

  @Test
  void shouldGetEmptyDescriptionFromNullDescription() {
    assertThat(JHipsterPropertyDescription.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDescriptionFromBlankDescription() {
    assertThat(JHipsterPropertyDescription.of(" ")).isEmpty();
  }

  @Test
  void shouldGetDescriptionFromActualDescription() {
    assertThat(JHipsterPropertyDescription.of("description")).contains(new JHipsterPropertyDescription("description"));
  }
}
