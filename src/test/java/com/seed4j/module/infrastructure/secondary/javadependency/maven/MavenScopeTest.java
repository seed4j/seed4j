package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenScopeTest {

  @Test
  void shouldGetNullScopeFromNullKey() {
    assertThat(MavenScope.from(null)).isNull();
  }

  @Test
  void shouldGetNullScopeFromUnknownKey() {
    assertThat(MavenScope.from("unknown")).isNull();
  }

  @Test
  void shouldGetScopeFromName() {
    assertThat(MavenScope.from("test")).isEqualTo(MavenScope.TEST);
  }
}
