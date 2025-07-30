package com.seed4j.shared.enumeration.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import com.seed4j.shared.error.domain.StandardErrorKey;
import org.junit.jupiter.api.Test;

@UnitTest
class UnmappableEnumExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnmappableEnumException exception = new UnmappableEnumException(StandardErrorKey.class, ErrorStatus.class);

    assertThat(exception.getMessage()).isEqualTo(
      "Can't map class com.seed4j.shared.error.domain.StandardErrorKey to class com.seed4j.shared.error.domain.ErrorStatus"
    );
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.key()).isEqualTo(EnumsErrorKey.UNMAPPABLE_ENUM);
  }
}
