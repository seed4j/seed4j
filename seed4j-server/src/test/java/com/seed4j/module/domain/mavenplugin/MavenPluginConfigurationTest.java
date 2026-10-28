package com.seed4j.module.domain.mavenplugin;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenPluginConfigurationTest {

  @Test
  void shouldGetValueAsString() {
    assertThat(new MavenPluginConfiguration("<fail>false</fail>")).hasToString("<fail>false</fail>");
  }
}
