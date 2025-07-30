package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterProjectBaseNameTest {

  @Test
  void shouldNotBuildWithInvalidProjectBaseName() {
    assertThatThrownBy(() -> new JHipsterProjectBaseName("invalid name")).isExactlyInstanceOf(InvalidProjectBaseNameException.class);
  }

  @Test
  void shouldGetDefaultProjectNameFromNullBaseName() {
    assertThat(new JHipsterProjectBaseName(null).get()).isEqualTo("jhipster");
  }

  @Test
  void shouldGetDefaultProjectBaseNameFromBlankName() {
    assertThat(new JHipsterProjectBaseName(" ").get()).isEqualTo("jhipster");
  }

  @Test
  void shouldGetProjectBaseName() {
    assertThat(new JHipsterProjectBaseName("myProject").get()).isEqualTo("myProject");
  }

  @Test
  void shouldUncapitalizeProjectBaseName() {
    assertThat(new JHipsterProjectBaseName("MyProject").uncapitalized()).isEqualTo("myProject");
  }

  @Test
  void shouldGetCapitalizedProjectBaseName() {
    assertThat(new JHipsterProjectBaseName("myProject").capitalized()).isEqualTo("MyProject");
  }

  @Test
  void shouldGetUppercasedProjectBaseName() {
    assertThat(new JHipsterProjectBaseName("MyProject").upperCased()).isEqualTo("MY_PROJECT");
  }

  @Test
  void shouldGetKebabCaseProjectBaseName() {
    assertThat(new JHipsterProjectBaseName("MyProject3").kebabCase()).isEqualTo("my-project3");
  }
}
