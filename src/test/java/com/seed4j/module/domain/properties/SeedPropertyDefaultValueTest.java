package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedPropertyDefaultValueTest {

  @Test
  void shouldGetEmptyDefaultValueFromNullValue() {
    assertThat(SeedPropertyDefaultValue.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDefaultValueFromBlankValue() {
    assertThat(SeedPropertyDefaultValue.of(" ")).isEmpty();
  }

  @Test
  void shouldGetDefaultValueFromActualDefaultValue() {
    assertThat(SeedPropertyDefaultValue.of("defaultValue")).contains(new SeedPropertyDefaultValue("defaultValue"));
  }
}
