package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

@UnitTest
class RegexReplacerTest {

  @Test
  void shouldNotBuildWithInvalidPattern() {
    assertThatThrownBy(() -> new RegexReplacer(always(), "{")).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotMatchNotMatchingPattern() {
    assertThat(new RegexReplacer(always(), "pouet").notMatchIn("toto")).isTrue();
  }

  @Test
  void shouldMatchMatchingPattern() {
    assertThat(new RegexReplacer(always(), "pouet").notMatchIn("pouet")).isFalse();
  }

  @Test
  void shouldGetPatternAsSearchMatcher() {
    assertThat(new RegexReplacer(always(), "pouet").searchMatcher()).isEqualTo("pouet");
  }
}
