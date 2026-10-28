package com.seed4j.module.domain.replacement;

import static com.seed4j.module.domain.replacement.ReplacementCondition.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.GeneratorException;
import org.junit.jupiter.api.Test;

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
