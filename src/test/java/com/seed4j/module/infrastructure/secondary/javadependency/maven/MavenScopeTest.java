package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

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
}
