package com.seed4j.shared.slug.domain;

import static com.seed4j.module.domain.resource.SeedModuleRank.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.resource.SeedModuleRank;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class Seed4JModuleSlugTest {

  @MethodSource("shouldGetRank")
  @ParameterizedTest
  void shouldGetRank(Seed4JModuleSlug seed4JModuleSlug, SeedModuleRank expectedRank) {
    assertThat(Seed4JModuleSlug.getRank(seed4JModuleSlug.get())).contains(expectedRank);
  }

  private static Stream<Arguments> shouldGetRank() {
    return Stream.of(
      Arguments.of(Seed4JModuleSlug.INIT, RANK_S),
      Arguments.of(Seed4JModuleSlug.SPRING_BOOT, RANK_S),
      Arguments.of(Seed4JModuleSlug.SPRING_BOOT_ASYNC, RANK_A)
    );
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = { "growth", " " })
  void shouldNotGetRank(String slug) {
    assertThat(Seed4JModuleSlug.getRank(slug)).isEmpty();
  }
}
