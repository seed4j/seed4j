package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class FileStartReplacerTest {

  @Test
  void shouldGetSearchMatcher() {
    assertThat(new FileStartReplacer(ReplacementCondition.always()).searchMatcher()).isEqualTo("--file-start--");
  }
}
