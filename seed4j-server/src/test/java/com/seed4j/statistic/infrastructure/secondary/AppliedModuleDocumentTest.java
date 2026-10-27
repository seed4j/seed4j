package com.seed4j.statistic.infrastructure.secondary;

import static com.seed4j.statistic.domain.AppliedModuleFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class AppliedModuleDocumentTest {

  @Test
  void shouldConvertFromAndToDomain() {
    assertThat(AppliedModuleDocument.from(appliedModule()).toDomain()).usingRecursiveComparison().isEqualTo(appliedModule());
  }
}
