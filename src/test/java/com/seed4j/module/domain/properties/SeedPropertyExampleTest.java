package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedPropertyExampleTest {

  @Test
  void shouldGetEmptyExampleFromNullExample() {
    assertThat(SeedPropertyExample.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyExampleFromBlankExample() {
    assertThat(SeedPropertyExample.of(" ")).isEmpty();
  }

  @Test
  void shouldGetExampleFromActualExample() {
    assertThat(SeedPropertyExample.of("example")).contains(new SeedPropertyExample("example"));
  }

  @Test
  void shouldGetValueFromGet() {
    assertThat(SeedPropertyExample.of("example").orElseThrow().get()).isEqualTo(new SeedPropertyExample("example").get());
  }
}
