package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javadependency.JavaDependencyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@UnitTest
class MavenTypeTest {

  @Test
  void shouldGetEmptyTypeForNullString() {
    assertThat(MavenType.from((String) null)).isEmpty();
  }

  @Test
  void shouldGetEmptyTypeForUnknownType() {
    assertThat(MavenType.from("unknown")).isEmpty();
  }

  @Test
  void shouldGetTypeFromPomType() {
    assertThat(MavenType.from("pom")).contains(MavenType.POM);
  }

  @ParameterizedTest
  @EnumSource(JavaDependencyType.class)
  void shouldMapFromToJavaDependencyType(JavaDependencyType type) {
    assertThat(MavenType.from(type).toJavaDependencyType()).isEqualTo(type);
  }
}
