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
    assertThatThrownBy(() -> new JHipsterModuleSlug(slug)).isExactlyInstanceOf(InvalidJHipsterSlugException.class);
  }

  @Test
  void shouldSortModules() {
    Stream<JHipsterModuleSlug> modules = Stream.of(
      new JHipsterModuleSlug("root"),
      new JHipsterModuleSlug("init"),
      new JHipsterModuleSlug("dummy"),
      new JHipsterModuleSlug("init")
    ).sorted();

    assertThat(modules).containsExactly(
      new JHipsterModuleSlug("init"),
      new JHipsterModuleSlug("init"),
      new JHipsterModuleSlug("dummy"),
      new JHipsterModuleSlug("root")
    );
  }

  @Test
  void testToStringShowsSlug() {
    assertThat(new JHipsterModuleSlug("init")).hasToString("init");
  }
}
