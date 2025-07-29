package com.seed4j.module.domain.replacement;

import static com.seed4j.module.domain.replacement.ReplacementCondition.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

@UnitTest
class RegexNeedleBeforeReplacerTest {

  @Test
  void shouldNotMatchNotMatchingRegex() {
    RegexNeedleBeforeReplacer replacer = new RegexNeedleBeforeReplacer(always(), Pattern.compile("pattern"));

    assertThat(replacer.notMatchIn("content")).isTrue();
  }

  @Test
  void shouldMatchMatchingRegex() {
    RegexNeedleBeforeReplacer replacer = new RegexNeedleBeforeReplacer(always(), Pattern.compile("cont[en]{2}t"));

    assertThat(replacer.notMatchIn("content")).isFalse();
  }

  @Test
  void shouldNotReplaceNotMatchingNeedle() {
    RegexNeedleBeforeReplacer replacer = new RegexNeedleBeforeReplacer(always(), Pattern.compile("ne{1,2}dle"));

    String updatedContent = replacer.replacement().apply("content", "replacement");

    assertThat(updatedContent).isEqualTo("content");
  }

  @Test
  void shouldReplaceLineStartNeedle() {
    RegexNeedleBeforeReplacer replacer = new RegexNeedleBeforeReplacer(always(), Pattern.compile("^<!-- ne{1,2}dle", Pattern.MULTILINE));

    String updatedContent = replacer
      .replacement()
      .apply(
        """
        <root>
        <!-- needle !-->
        </root>
        """,
        "<element />"
      );

    assertThat(updatedContent).isEqualTo(
        """
        <root>
        <element />
        <!-- needle !-->
        </root>
        """
      );
  }

  @Test
  void shouldReplaceLinePartNeedle() {
    RegexNeedleBeforeReplacer replacer = new RegexNeedleBeforeReplacer(always(), Pattern.compile("<!-- ne{1,2}dle", Pattern.MULTILINE));

    String updatedContent = replacer
      .replacement()
      .apply(
        """
        <root>
          <!-- needle !-->

          <!-- needle !-->
        </root>
        """,
        "<element />"
      );

    assertThat(updatedContent).isEqualTo(
        """
        <root>
        <element />
          <!-- needle !-->

        <element />
          <!-- needle !-->
        </root>
        """
      );
  }

  @Test
  void shouldGetPatternAsSearchMatcher() {
    RegexNeedleBeforeReplacer replacer = new RegexNeedleBeforeReplacer(always(), Pattern.compile("cont[en]{2}t"));

    assertThat(replacer.searchMatcher()).isEqualTo("cont[en]{2}t");
  }
}
