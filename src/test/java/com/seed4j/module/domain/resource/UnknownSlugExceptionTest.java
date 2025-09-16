package com.seed4j.module.domain.resource;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class UnknownSlugExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownSlugException exception = new UnknownSlugException(new Seed4JModuleSlug("unknown-slug"));

    assertThat(exception.getMessage()).isEqualTo("Module unknown-slug does not exist");
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.key()).isEqualTo(ResourceErrorKey.UNKNOWN_SLUG);
    assertThat(exception.parameters()).containsOnly(entry("slug", "unknown-slug"));
  }
}
