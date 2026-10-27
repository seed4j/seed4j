package com.seed4j.module.domain.replacement;

import com.seed4j.shared.error.domain.GeneratorException;

class UnknownCurrentValueException extends GeneratorException {

  public UnknownCurrentValueException(String currentValue, String content) {
    super(badRequest(ReplacementErrorKey.UNKNOWN_CURRENT_VALUE).message(buildMessage(currentValue, content)));
  }

  private static String buildMessage(String currentValue, String content) {
    return "Can't find \"%s\" in %s".formatted(currentValue, content);
  }
}
