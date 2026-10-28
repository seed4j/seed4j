package com.seed4j.module.domain.mavenplugin;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenPluginExecutionIdTest {

  @Test
  void shouldGetValueAsString() {
    assertThat(new MavenPluginExecutionId("enforce-versions")).hasToString("enforce-versions");
  }
}
