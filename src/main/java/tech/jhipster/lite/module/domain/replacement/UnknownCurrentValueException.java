package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.shared.error.domain.GeneratorException;

class UnknownCurrentValueException extends GeneratorException {

  public UnknownCurrentValueException(String currentValue, String content) {
    super(badRequest(ReplacementErrorKey.UNKNOWN_CURRENT_VALUE).message(buildMessage(currentValue, content)));
  }

  private static String buildMessage(String currentValue, String content) {
    return new StringBuilder().append("Can't find \"").append(currentValue).append("\" in ").append(content).toString();
  }
}
