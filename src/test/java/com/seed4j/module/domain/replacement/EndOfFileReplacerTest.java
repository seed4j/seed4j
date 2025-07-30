package com.seed4j.module.domain.replacement;

import static com.seed4j.module.domain.replacement.ReplacementCondition.always;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class EndOfFileReplacerTest {

  @Test
  void shouldMatchMatchingRegex() {
    EndOfFileReplacer replacer = new EndOfFileReplacer(always());

    assertThat(replacer.notMatchIn("content")).isFalse();
  }

  @Test
  void shouldReplaceLineEnd() {
    EndOfFileReplacer replacer = new EndOfFileReplacer(always());

    String updatedContent = replacer
      .replacement()
      .apply(
        """
        <root>
        </root>
        """,
        "<element />"
      );

    assertThat(updatedContent).isEqualTo(
        """
        <root>
        </root>
        <element />
        """
      );
  }

  @Test
  void shouldGetPatternAsSearchMatcher() {
    EndOfFileReplacer replacer = new EndOfFileReplacer(always());

    assertThat(replacer.searchMatcher()).isEqualTo("\\z");
  }
}
