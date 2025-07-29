package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterPropertyDefaultValueTest {

  @Test
  void shouldGetEmptyDefaultValueFromNullValue() {
    assertThat(JHipsterPropertyDefaultValue.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDefaultValueFromBlankValue() {
    assertThat(JHipsterPropertyDefaultValue.of(" ")).isEmpty();
  }

  @Test
  void shouldGetDefaultValueFromActualDefaultValue() {
    assertThat(JHipsterPropertyDefaultValue.of("defaultValue")).contains(new JHipsterPropertyDefaultValue("defaultValue"));
  }
}
