package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedPropertyDescriptionTest {

  @Test
  void shouldGetEmptyDescriptionFromNullDescription() {
    assertThat(SeedPropertyDescription.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDescriptionFromBlankDescription() {
    assertThat(SeedPropertyDescription.of(" ")).isEmpty();
  }

  @Test
  void shouldGetDescriptionFromActualDescription() {
    assertThat(SeedPropertyDescription.of("description")).contains(new SeedPropertyDescription("description"));
  }
}
