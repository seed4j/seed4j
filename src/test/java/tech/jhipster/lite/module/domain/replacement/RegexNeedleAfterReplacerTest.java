package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.*;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class RegexNeedleAfterReplacerTest {

  @Test
  void shouldNotMatchNotMatchingRegex() {
    RegexNeedleAfterReplacer replacer = new RegexNeedleAfterReplacer(always(), Pattern.compile("pattern"));

    assertThat(replacer.notMatchIn("content")).isTrue();
  }

  @Test
  void shouldMatchMatchingRegex() {
    RegexNeedleAfterReplacer replacer = new RegexNeedleAfterReplacer(always(), Pattern.compile("cont[en]{2}t"));

    assertThat(replacer.notMatchIn("content")).isFalse();
  }

  @Test
  void shouldNotReplaceNotMatchingNeedle() {
    RegexNeedleAfterReplacer replacer = new RegexNeedleAfterReplacer(always(), Pattern.compile("ne{1,2}dle"));

    String updatedContent = replacer.replacement().apply("content", "replacement");

    assertThat(updatedContent).isEqualTo("content");
  }

  @Test
  void shouldReplaceLineEndNeedle() {
    RegexNeedleAfterReplacer replacer = new RegexNeedleAfterReplacer(always(), Pattern.compile("ne{1,2}dle !-->$", Pattern.MULTILINE));

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
      <!-- needle !-->
      <element />
      </root>
      """
    );
  }

  @Test
  void shouldReplaceLinePartNeedle() {
    RegexNeedleAfterReplacer replacer = new RegexNeedleAfterReplacer(always(), Pattern.compile("<!-- ne{1,2}dle", Pattern.MULTILINE));

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
        <!-- needle !-->
      <element />

        <!-- needle !-->
      <element />
      </root>
      """
    );
  }

  @Test
  void shouldGetPatternAsSearchMatcher() {
    RegexNeedleAfterReplacer replacer = new RegexNeedleAfterReplacer(always(), Pattern.compile("cont[en]{2}t"));

    assertThat(replacer.searchMatcher()).isEqualTo("cont[en]{2}t");
  }
}
