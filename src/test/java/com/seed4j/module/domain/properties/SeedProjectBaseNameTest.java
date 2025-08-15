package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedProjectBaseNameTest {

  @Test
  void shouldNotBuildWithInvalidProjectBaseName() {
    assertThatThrownBy(() -> new SeedProjectBaseName("invalid name")).isExactlyInstanceOf(InvalidProjectBaseNameException.class);
  }

  @Test
  void shouldGetDefaultProjectNameFromNullBaseName() {
    assertThat(new SeedProjectBaseName(null).get()).isEqualTo("seed4j");
  }

  @Test
  void shouldGetDefaultProjectBaseNameFromBlankName() {
    assertThat(new SeedProjectBaseName(" ").get()).isEqualTo("seed4j");
  }

  @Test
  void shouldGetProjectBaseName() {
    assertThat(new SeedProjectBaseName("myProject").get()).isEqualTo("myProject");
  }

  @Test
  void shouldUncapitalizeProjectBaseName() {
    assertThat(new SeedProjectBaseName("MyProject").uncapitalized()).isEqualTo("myProject");
  }

  @Test
  void shouldGetCapitalizedProjectBaseName() {
    assertThat(new SeedProjectBaseName("myProject").capitalized()).isEqualTo("MyProject");
  }

  @Test
  void shouldGetUppercasedProjectBaseName() {
    assertThat(new SeedProjectBaseName("MyProject").upperCased()).isEqualTo("MY_PROJECT");
  }

  @Test
  void shouldGetKebabCaseProjectBaseName() {
    assertThat(new SeedProjectBaseName("MyProject3").kebabCase()).isEqualTo("my-project3");
  }
}
