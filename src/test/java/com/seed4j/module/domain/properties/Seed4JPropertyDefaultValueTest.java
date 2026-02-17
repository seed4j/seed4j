package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JPropertyDefaultValueTest {

  @Test
  void shouldGetEmptyDefaultValueFromBlankValue() {
    assertThat(Seed4JPropertyDefaultValue.of(" ")).isEmpty();
  }

  @Test
  void shouldGetDefaultValueFromActualDefaultValue() {
    assertThat(Seed4JPropertyDefaultValue.of("defaultValue")).contains(new Seed4JPropertyDefaultValue("defaultValue"));
  }
}
