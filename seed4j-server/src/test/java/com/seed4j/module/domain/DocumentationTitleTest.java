package com.seed4j.module.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class DocumentationTitleTest {

  @Test
  void shouldCleanFilename() {
    assertThat(new DocumentationTitle("Special chars & $ éè").filename()).isEqualTo("special-chars-ee");
  }
}
