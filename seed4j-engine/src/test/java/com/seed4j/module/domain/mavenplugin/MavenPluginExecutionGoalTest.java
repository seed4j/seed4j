package com.seed4j.module.domain.mavenplugin;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenPluginExecutionGoalTest {

  @Test
  void shouldGetValueAsString() {
    assertThat(new MavenPluginExecutionGoal("enforce")).hasToString("enforce");
  }
}
