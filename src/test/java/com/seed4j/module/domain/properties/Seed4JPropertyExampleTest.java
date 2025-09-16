package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JPropertyExampleTest {

  @Test
  void shouldGetEmptyExampleFromNullExample() {
    assertThat(Seed4JPropertyExample.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyExampleFromBlankExample() {
    assertThat(Seed4JPropertyExample.of(" ")).isEmpty();
  }

  @Test
  void shouldGetExampleFromActualExample() {
    assertThat(Seed4JPropertyExample.of("example")).contains(new Seed4JPropertyExample("example"));
  }

  @Test
  void shouldGetValueFromGet() {
    assertThat(Seed4JPropertyExample.of("example").orElseThrow().get()).isEqualTo(new Seed4JPropertyExample("example").get());
  }
}
