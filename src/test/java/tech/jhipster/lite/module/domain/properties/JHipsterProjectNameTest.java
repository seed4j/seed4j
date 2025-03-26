package tech.jhipster.lite.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterProjectNameTest {

  @Test
  void shouldGetDefaultNameFromNullName() {
    assertThat(new JHipsterProjectName(null).get()).isEqualTo("JHipster Project");
  }

  @Test
  void shouldGetDefaultNameFromBlankName() {
    assertThat(new JHipsterProjectName(" ").get()).isEqualTo("JHipster Project");
  }

  @Test
  void shouldGetProjectNameFromActualName() {
    assertThat(new JHipsterProjectName("My project").get()).isEqualTo("My project");
  }
}
