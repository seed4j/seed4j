package tech.jhipster.lite.shared.error.infrastructure.primary;

import tech.jhipster.lite.shared.error.domain.GeneratorException;

public final class GeneratorExceptionFactory {

  private GeneratorExceptionFactory() {}

  public static final GeneratorException buildEmptyException() {
    return GeneratorException.builder(null).build();
  }
}
