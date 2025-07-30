package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterPropertyExampleTest {

  @Test
  void shouldGetEmptyExampleFromNullExample() {
    assertThat(JHipsterPropertyExample.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyExampleFromBlankExample() {
    assertThat(JHipsterPropertyExample.of(" ")).isEmpty();
  }

  @Test
  void shouldGetExampleFromActualExample() {
    assertThat(JHipsterPropertyExample.of("example")).contains(new JHipsterPropertyExample("example"));
  }

  @Test
  void shouldGetValueFromGet() {
    assertThat(JHipsterPropertyExample.of("example").orElseThrow().get()).isEqualTo(new JHipsterPropertyExample("example").get());
  }
}
