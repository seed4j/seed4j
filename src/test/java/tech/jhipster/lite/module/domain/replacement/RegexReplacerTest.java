package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    assertThat(new RegexReplacer(always(), "hello").notMatchIn("toto")).isTrue();
  }

  @Test
  void shouldMatchMatchingPattern() {
    assertThat(new RegexReplacer(always(), "hello").notMatchIn("hello")).isFalse();
  }

  @Test
  void shouldGetPatternAsSearchMatcher() {
    assertThat(new RegexReplacer(always(), "hello").searchMatcher()).isEqualTo("hello");
  }
}
