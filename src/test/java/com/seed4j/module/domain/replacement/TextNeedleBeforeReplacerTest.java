package com.seed4j.module.domain.replacement;

import static com.seed4j.module.domain.replacement.ReplacementCondition.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class TextNeedleBeforeReplacerTest {

  @Test
  void shouldNotMatchUnknownText() {
    assertThat(new TextNeedleBeforeReplacer(always(), "unknown").notMatchIn("content")).isTrue();
  }

  @Test
  void shouldMatchKnownText() {
    assertThat(new TextNeedleBeforeReplacer(always(), "known").notMatchIn("unknown")).isFalse();
  }

  @Test
  void shouldNotReplaceUnknownNeedle() {
    TextNeedleBeforeReplacer replacer = new TextNeedleBeforeReplacer(always(), "<!-- needle !-->");

    String updatedContent = replacer.replacement().apply("<root />", "<element />");

    assertThat(updatedContent).isEqualTo("<root />");
  }

  @Test
  void shouldInsertTextLineBeforeFirstLineNeedle() {
    TextNeedleBeforeReplacer replacer = new TextNeedleBeforeReplacer(always(), "<!-- needle !-->");

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
        <element />
        <!-- needle !-->
        """
      );
  }

  @Test
  void shouldInsertTextLineBeforeNeedleLine() {
    TextNeedleBeforeReplacer replacer = new TextNeedleBeforeReplacer(always(), "<!-- needle !-->");

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
  void shouldInsertTextLineBeforeNeedleLinePart() {
    TextNeedleBeforeReplacer replacer = new TextNeedleBeforeReplacer(always(), "<!-- needle !-->");

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
  void shouldReplaceMultipleNeedles() {
    TextNeedleBeforeReplacer replacer = new TextNeedleBeforeReplacer(always(), "<!-- needle !-->");

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
        <element />
          <!-- needle !-->


        <element />
          <!-- needle !-->
        <element />
          <!-- needle !--> with trailing text
        </root>
        """
      );
  }

  @Test
  void shouldGetTextAsMatcher() {
    assertThat(new TextNeedleBeforeReplacer(always(), "known").searchMatcher()).isEqualTo("known");
  }
}
