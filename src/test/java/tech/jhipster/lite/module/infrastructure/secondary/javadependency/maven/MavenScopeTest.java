package tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

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
