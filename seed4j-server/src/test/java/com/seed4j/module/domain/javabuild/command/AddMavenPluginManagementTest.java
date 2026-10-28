package com.seed4j.module.domain.javabuild.command;

import static com.seed4j.module.domain.Seed4JEngineFixture.mavenEnforcerPlugin;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import org.junit.jupiter.api.Test;

@UnitTest
class AddMavenPluginManagementTest {

  @Test
  void shouldGetPluginAndVersionsAsString() {
    AddMavenPluginManagement command = AddMavenPluginManagement.builder()
      .plugin(mavenEnforcerPlugin())
      .pluginVersion(new JavaDependencyVersion("maven-enforcer-plugin", "3.5.0"))
      .addDependencyVersion(new JavaDependencyVersion("json-web-token", "0.12.6"))
      .build();

    assertThat(command.toString()).contains("artifactId=maven-enforcer-plugin", "3.5.0", "0.12.6");
  }
}
