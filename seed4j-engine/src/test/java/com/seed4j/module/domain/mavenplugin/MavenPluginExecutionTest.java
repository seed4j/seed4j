package com.seed4j.module.domain.mavenplugin;

import static com.seed4j.module.domain.Seed4JModule.pluginExecution;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenPluginExecutionTest {

  @Test
  void shouldGetExecutionAsString() {
    MavenPluginExecution execution = pluginExecution()
      .goals("enforce")
      .id("enforce-versions")
      .phase(MavenBuildPhase.VERIFY)
      .configuration("<fail>false</fail>")
      .build();

    assertThat(execution.toString()).contains("goals=[enforce]", "id=enforce-versions", "phase=VERIFY", "configuration=<fail>false</fail>");
  }
}
