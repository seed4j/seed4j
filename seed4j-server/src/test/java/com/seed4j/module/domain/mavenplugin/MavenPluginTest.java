package com.seed4j.module.domain.mavenplugin;

import static com.seed4j.module.domain.Seed4JEngineFixture.mavenEnforcerPluginManagement;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenPluginTest {

  @Test
  void shouldGetPluginAsString() {
    assertThat(mavenEnforcerPluginManagement().toString()).contains(
      "artifactId=maven-enforcer-plugin",
      "versionSlug=maven-enforcer-plugin",
      "enforce-versions"
    );
  }
}
