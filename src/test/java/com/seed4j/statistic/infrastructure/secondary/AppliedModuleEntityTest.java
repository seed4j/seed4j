package com.seed4j.statistic.infrastructure.secondary;

import static com.seed4j.statistic.domain.AppliedModuleFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class AppliedModuleEntityTest {

  @Test
  void shouldConvertFromAndToDomain() {
    assertThat(AppliedModuleEntity.from(appliedModule()).toDomain()).usingRecursiveComparison().isEqualTo(appliedModule());
  }
}
