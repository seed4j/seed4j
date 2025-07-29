package com.seed4j.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class FileStartReplacerTest {

  @Test
  void shouldGetSearchMatcher() {
    assertThat(new FileStartReplacer(ReplacementCondition.always()).searchMatcher()).isEqualTo("--file-start--");
  }
}
