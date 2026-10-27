package com.seed4j.shared.error.infrastructure.primary;

import com.seed4j.shared.error.domain.GeneratorException;

public final class GeneratorExceptionFactory {

  private GeneratorExceptionFactory() {}

  public static GeneratorException buildEmptyException() {
    return GeneratorException.builder(null).build();
  }
}
