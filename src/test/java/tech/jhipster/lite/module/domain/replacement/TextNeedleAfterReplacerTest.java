package tech.jhipster.lite.module.domain.replacement;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.always;

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
  void shouldInsertTextLineAfterLastLineNeedle() {
    TextNeedleAfterReplacer replacer = new TextNeedleAfterReplacer(always(), "<!-- needle !-->");

    String updatedContent = replacer.replacement().apply("""
            <!-- needle !-->
            """, "<element />");

    assertThat(updatedContent).isEqualTo("""
        <!-- needle !-->
        <element />
        """);
  }

  @Test
  void shouldInsertTextLineAfterLastLineWithoutCarriageReturnNeedle() {
    TextNeedleAfterReplacer replacer = new TextNeedleAfterReplacer(always(), "<!-- needle !-->");

    String updatedContent = replacer.replacement().apply("<!-- needle !-->", "<element />");

    assertThat(updatedContent).isEqualTo("""
        <!-- needle !-->
        <element />""");
  }

  @Test
  void shouldInsertTextLineAfterNeedleLine() {
    TextNeedleAfterReplacer replacer = new TextNeedleAfterReplacer(always(), "<!-- needle !-->");

    String updatedContent = replacer
      .replacement()
      .apply("""
            <root>
            <!-- needle !-->
            </root>
            """, "<element />");

    assertThat(updatedContent).isEqualTo("""
        <root>
        <!-- needle !-->
        <element />
        </root>
        """);
  }

  @Test
  void shouldInsertTextLineAfterNeedleLinePart() {
    TextNeedleAfterReplacer replacer = new TextNeedleAfterReplacer(always(), "<!-- needle !-->");

    String updatedContent = replacer
      .replacement()
      .apply("""
            <root>
              <!-- needle !-->

            </root>
            """, "<element />");

    assertThat(updatedContent).isEqualTo("""
        <root>
          <!-- needle !-->
        <element />

        </root>
        """);
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
              <!-- needle !--> with trailling text
            </root>
            """,
        "<element />"
      );

    assertThat(updatedContent)
      .isEqualTo(
        """
        <root>
          <!-- needle !-->
        <element />

          <!-- needle !-->
        <element />
          <!-- needle !--> with trailling text
        <element />
        </root>
        """
      );
  }

  @Test
  void shouldGetTextAsMatcher() {
    assertThat(new TextNeedleAfterReplacer(always(), "known").searchMatcher()).isEqualTo("known");
  }
}
