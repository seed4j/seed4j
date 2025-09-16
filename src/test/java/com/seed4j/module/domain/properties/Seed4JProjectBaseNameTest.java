package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JProjectBaseNameTest {

  @Test
  void shouldNotBuildWithInvalidProjectBaseName() {
    assertThatThrownBy(() -> new Seed4JProjectBaseName("invalid name")).isExactlyInstanceOf(InvalidProjectBaseNameException.class);
  }

  @Test
  void shouldGetDefaultProjectNameFromNullBaseName() {
    assertThat(new Seed4JProjectBaseName(null).get()).isEqualTo("seed4j");
  }

  @Test
  void shouldGetDefaultProjectBaseNameFromBlankName() {
    assertThat(new Seed4JProjectBaseName(" ").get()).isEqualTo("seed4j");
  }

  @Test
  void shouldGetProjectBaseName() {
    assertThat(new Seed4JProjectBaseName("myProject").get()).isEqualTo("myProject");
  }

  @Test
  void shouldUncapitalizeProjectBaseName() {
    assertThat(new Seed4JProjectBaseName("MyProject").uncapitalized()).isEqualTo("myProject");
  }

  @Test
  void shouldGetCapitalizedProjectBaseName() {
    assertThat(new Seed4JProjectBaseName("myProject").capitalized()).isEqualTo("MyProject");
  }

  @Test
  void shouldGetUppercasedProjectBaseName() {
    assertThat(new Seed4JProjectBaseName("MyProject").upperCased()).isEqualTo("MY_PROJECT");
  }

  @Test
  void shouldGetKebabCaseProjectBaseName() {
    assertThat(new Seed4JProjectBaseName("MyProject3").kebabCase()).isEqualTo("my-project3");
  }
}
