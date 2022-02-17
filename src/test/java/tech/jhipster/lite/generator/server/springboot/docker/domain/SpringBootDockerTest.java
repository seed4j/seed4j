package tech.jhipster.lite.generator.server.springboot.docker.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class SpringBootDockerTest {

  @Test
  void shouldGetDockerBaseImage() {
    assertThat(SpringBootDocker.getDockerBaseImage()).isEqualTo("eclipse-temurin:17-jre-focal");
  }

  @Test
  void shouldGetDockerPlatformArchitecture() {
    assertThat(SpringBootDocker.getDockerPlatformArchitecture()).isEqualTo("amd64");
  }
}
