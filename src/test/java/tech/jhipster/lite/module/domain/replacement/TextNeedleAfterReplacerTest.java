package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class TextNeedleAfterReplacerTest {

  @Test
  void shouldNotMatchUnknownText() {
    assertThat(new TextNeedleAfterReplacer(always(), "unknown").notMatchIn("content")).isTrue();
  }

  @Test
  void shouldMatchKnownText() {
    assertThat(new TextNeedleAfterReplacer(always(), "known").notMatchIn("unknown")).isFalse();
  }

  @Test
  void shouldNotReplaceUnknownNeedle() {
    TextNeedleAfterReplacer replacer = new TextNeedleAfterReplacer(always(), "<!-- needle !-->");

    String updatedContent = replacer.replacement().apply("<root />", "<element />");

    assertThat(updatedContent).isEqualTo("<root />");
  }

  @Test
  void shouldInsertTextLineBeforeFirstLineNeedle() {
    TextNeedleAfterReplacer replacer = new TextNeedleAfterReplacer(always(), "<!-- needle !-->");

    String updatedContent = replacer
      .replacement()
      .apply(
        """
        <!-- needle !-->
        """,
        "<element />"
      );

    assertThat(updatedContent).isEqualTo(
      """
      <!-- needle !-->
      <element />
      """
    );
  }

  @Test
  void shouldInsertTextLineBeforeNeedleLine() {
    TextNeedleAfterReplacer replacer = new TextNeedleAfterReplacer(always(), "<!-- needle !-->");

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
  void shouldInsertTextLineBeforeNeedleLinePart() {
    TextNeedleAfterReplacer replacer = new TextNeedleAfterReplacer(always(), "<!-- needle !-->");

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
  void shouldReplaceMultipleNeedles() {
    TextNeedleAfterReplacer replacer = new TextNeedleAfterReplacer(always(), "<!-- needle !-->");

    String updatedContent = replacer
      .replacement()
      .apply(
        """
        <root>
          <!-- needle !-->


          <!-- needle !-->
          <!-- needle !--> with trailing text
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
        <!-- needle !-->
      <element /> with trailing text
      </root>
      """
    );
  }

  @Test
  void shouldGetTextAsMatcher() {
    assertThat(new TextNeedleAfterReplacer(always(), "known").searchMatcher()).isEqualTo("known");
  }
}
