package com.seed4j.module.domain.resource;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class DuplicatedSlugExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    DuplicatedSlugException exception = new DuplicatedSlugException();

    assertThat(exception.getMessage()).isEqualTo("Found a duplicated module slug, ensure that slugs are unique");
    assertThat(exception.key()).isEqualTo(ResourceErrorKey.DUPLICATED_SLUG);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
  }
}
