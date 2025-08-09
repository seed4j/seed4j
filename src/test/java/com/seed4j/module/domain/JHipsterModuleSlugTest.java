package com.seed4j.module.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class JHipsterModuleSlugTest {

  @ParameterizedTest
  @ValueSource(strings = { "Invalid", "this is invalid" })
  void shouldNotBuildInvalidSlug(String slug) {
    assertThatThrownBy(() -> new SeedModuleSlug(slug)).isExactlyInstanceOf(InvalidJHipsterSlugException.class);
  }

  @Test
  void shouldSortModules() {
    Stream<SeedModuleSlug> modules = Stream.of(
      new SeedModuleSlug("root"),
      new SeedModuleSlug("init"),
      new SeedModuleSlug("dummy"),
      new SeedModuleSlug("init")
    ).sorted();

    assertThat(modules).containsExactly(
      new SeedModuleSlug("init"),
      new SeedModuleSlug("init"),
      new SeedModuleSlug("dummy"),
      new SeedModuleSlug("root")
    );
  }

  @Test
  void testToStringShowsSlug() {
    assertThat(new SeedModuleSlug("init")).hasToString("init");
  }
}
