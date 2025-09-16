package com.seed4j.shared.slug.domain;

import static com.seed4j.module.domain.resource.Seed4JModuleRank.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.resource.Seed4JModuleRank;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class Seed4JCoreModuleSlugTest {

  @MethodSource("shouldGetRank")
  @ParameterizedTest
  void shouldGetRank(Seed4JCoreModuleSlug seed4JCoreModuleSlug, Seed4JModuleRank expectedRank) {
    assertThat(Seed4JCoreModuleSlug.getRank(seed4JCoreModuleSlug.get())).contains(expectedRank);
  }

  private static Stream<Arguments> shouldGetRank() {
    return Stream.of(
      Arguments.of(Seed4JCoreModuleSlug.INIT, RANK_S),
      Arguments.of(Seed4JCoreModuleSlug.SPRING_BOOT, RANK_S),
      Arguments.of(Seed4JCoreModuleSlug.SPRING_BOOT_ASYNC, RANK_A)
    );
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = { "growth", " " })
  void shouldNotGetRank(String slug) {
    assertThat(Seed4JCoreModuleSlug.getRank(slug)).isEmpty();
  }
}
