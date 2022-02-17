package tech.jhipster.lite.generator.server.sonar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class SonarTest {

  @Test
  void shouldGetSonarqubeDockerVersion() {
    assertThat(Sonar.getSonarqubeDockerImage()).isEqualTo("sonarqube:9.2.4-community");
  }
}
