package com.seed4j.shared.enumeration.domain;

import com.seed4j.shared.error.domain.GeneratorException;

class UnmappableEnumException extends GeneratorException {

  public <T extends Enum<T>, U extends Enum<U>> UnmappableEnumException(Class<T> from, Class<U> to) {
    super(internalServerError(EnumsErrorKey.UNMAPPABLE_ENUM).message("Can't map " + from + " to " + to));
  }
}
