package tech.jhipster.lite.generator.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ProjectTest {

  @Test
  void shouldGetNodeVersion() {
    assertThat(Project.getNodeVersion()).isEqualTo(Project.NODE_VERSION);
  }
}
