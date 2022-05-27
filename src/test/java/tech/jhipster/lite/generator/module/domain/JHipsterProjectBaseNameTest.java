package tech.jhipster.lite.generator.module.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterProjectBaseNameTest {

  @Test
  void shouldNotBuildWithInvalidProjectName() {
    assertThatThrownBy(() -> new JHipsterProjectBaseName("invalid name")).isExactlyInstanceOf(InvalidProjectBaseNameException.class);
  }

  @Test
  void shouldGetDefaultPojectNameFromNullName() {
    assertThat(new JHipsterProjectBaseName(null).get()).isEqualTo("jhipster");
  }

  @Test
  void shouldGetDefaultPojectNameFromBlankName() {
    assertThat(new JHipsterProjectBaseName(" ").get()).isEqualTo("jhipster");
  }

  @Test
  void shouldGetProjectName() {
    assertThat(new JHipsterProjectBaseName("myProject").get()).isEqualTo("myProject");
  }

  @Test
  void shouldGetCapitalizedProjectName() {
    assertThat(new JHipsterProjectBaseName("myProject").capitalized()).isEqualTo("MyProject");
  }
}
