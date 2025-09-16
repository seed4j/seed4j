package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JPropertyDescriptionTest {

  @Test
  void shouldGetEmptyDescriptionFromNullDescription() {
    assertThat(Seed4JPropertyDescription.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDescriptionFromBlankDescription() {
    assertThat(Seed4JPropertyDescription.of(" ")).isEmpty();
  }

  @Test
  void shouldGetDescriptionFromActualDescription() {
    assertThat(Seed4JPropertyDescription.of("description")).contains(new Seed4JPropertyDescription("description"));
  }
}
