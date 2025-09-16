package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JPropertyDefaultValueTest {

  @Test
  void shouldGetEmptyDefaultValueFromNullValue() {
    assertThat(Seed4JPropertyDefaultValue.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDefaultValueFromBlankValue() {
    assertThat(Seed4JPropertyDefaultValue.of(" ")).isEmpty();
  }

  @Test
  void shouldGetDefaultValueFromActualDefaultValue() {
    assertThat(Seed4JPropertyDefaultValue.of("defaultValue")).contains(new Seed4JPropertyDefaultValue("defaultValue"));
  }
}
