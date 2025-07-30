package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenTypeTest {

  @Test
  void shouldGetEmptyTypeForNullType() {
    assertThat(MavenType.from(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyTypeForUnknownType() {
    assertThat(MavenType.from("unknown")).isEmpty();
  }

  @Test
  void shouldGetTypeFromPomType() {
    assertThat(MavenType.from("pom")).contains(MavenType.POM);
  }
}
