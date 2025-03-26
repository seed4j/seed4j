package tech.jhipster.lite.module.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class DocumentationTitleTest {

  @Test
  void shouldCleanFilename() {
    assertThat(new DocumentationTitle("Special chars & $ éè").filename()).isEqualTo("special-chars-ee");
  }
}
