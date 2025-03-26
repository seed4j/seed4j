package tech.jhipster.lite.shared.slug.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.module.domain.resource.JHipsterModuleRank.*;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleRank;

@UnitTest
class JHLiteModuleSlugTest {

  @MethodSource("shouldGetRank")
  @ParameterizedTest
  void shouldGetRank(JHLiteModuleSlug jhLiteModuleSlug, JHipsterModuleRank expectedRank) {
    assertThat(JHLiteModuleSlug.getRank(jhLiteModuleSlug.get())).contains(expectedRank);
  }

  private static Stream<Arguments> shouldGetRank() {
    return Stream.of(
      Arguments.of(JHLiteModuleSlug.INIT, RANK_S),
      Arguments.of(JHLiteModuleSlug.SPRING_BOOT, RANK_S),
      Arguments.of(JHLiteModuleSlug.SPRING_BOOT_ASYNC, RANK_A)
    );
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = { "chips", " " })
  void shouldNotGetRank(String slug) {
    assertThat(JHLiteModuleSlug.getRank(slug)).isEmpty();
  }
}
