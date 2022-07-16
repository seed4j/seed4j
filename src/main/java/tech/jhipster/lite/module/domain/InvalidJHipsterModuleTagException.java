package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.error.domain.GeneratorException;

class InvalidJHipsterModuleTagException extends GeneratorException {

  public InvalidJHipsterModuleTagException(String tag) {
    super(
      "The module tag \"" +
      tag +
      "\" is invalid (blank, bad format, whitespace...). Tag should be only lower case letters, numbers and hyphens (-)"
    );
  }
}
