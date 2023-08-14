package tech.jhipster.lite.module.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class IndentationTest {

  @Test
  void shouldGetIndentSizeWithoutIndentation() {
    assertThat(Indentation.from(null)).isEqualTo(Indentation.DEFAULT);
  }

  @Test
  void shouldGetIndentSizeForZeroIndentation() {
    assertThat(Indentation.from(0)).isEqualTo(Indentation.DEFAULT);
  }

  @Test
  void shouldGetIndentSizeForNegativeIndentation() {
    assertThat(Indentation.from(-1)).isEqualTo(Indentation.DEFAULT);
  }

  @Test
  void shouldGetIndentationFromActualIndentation() {
    assertThat(Indentation.from(42)).isEqualTo(new Indentation(42));
  }
}
