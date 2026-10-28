package com.seed4j.module.domain.javadependency;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class VersionTest {

  @Test
  void shouldGetValueAsString() {
    assertThat(new Version("1.2.3")).hasToString("1.2.3");
  }
}
