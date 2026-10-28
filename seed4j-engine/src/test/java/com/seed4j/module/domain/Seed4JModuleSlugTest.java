package com.seed4j.module.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class Seed4JModuleSlugTest {

  @ParameterizedTest
  @ValueSource(strings = { "Invalid", "this is invalid" })
  void shouldNotBuildInvalidSlug(String slug) {
    assertThatThrownBy(() -> new Seed4JModuleSlug(slug)).isExactlyInstanceOf(InvalidSeed4JSlugException.class);
  }

  @Test
  void shouldSortModules() {
    Stream<Seed4JModuleSlug> modules = Stream.of(
      new Seed4JModuleSlug("root"),
      new Seed4JModuleSlug("init"),
      new Seed4JModuleSlug("dummy"),
      new Seed4JModuleSlug("init")
    ).sorted();

    assertThat(modules).containsExactly(
      new Seed4JModuleSlug("init"),
      new Seed4JModuleSlug("init"),
      new Seed4JModuleSlug("dummy"),
      new Seed4JModuleSlug("root")
    );
  }

  @Test
  void testToStringShowsSlug() {
    assertThat(new Seed4JModuleSlug("init")).hasToString("init");
  }
}
