package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@UnitTest
class MavenScopeTest {

  @Test
  void shouldGetEmptyScopeFromNullKey() {
    assertThat(MavenScope.from((String) null)).isEmpty();
  }

  @Test
  void shouldGetNullScopeFromUnknownKey() {
    assertThat(MavenScope.from("unknown")).isEmpty();
  }

  @Test
  void shouldGetScopeFromName() {
    assertThat(MavenScope.from("test")).hasValue(MavenScope.TEST);
  }

  @ParameterizedTest
  @EnumSource(JavaDependencyScope.class)
  void shouldMapFromToJavaDependencyScope(JavaDependencyScope scope) {
    assertThat(MavenScope.from(scope).toJavaDependencyScope()).isEqualTo(scope);
  }
}
