package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.GeneratorException;

class InvalidSeed4JModuleTagException extends GeneratorException {

  public InvalidSeed4JModuleTagException(String tag) {
    super(internalServerError(ResourceErrorKey.INVALID_TAG).message(buildMessage(tag)).addParameter("tag", tag));
  }

  private static String buildMessage(String tag) {
    return new StringBuilder()
      .append("The module tag \"")
      .append(tag)
      .append("\" is invalid (blank, bad format, whitespace...). Tag should be only lower case letters, numbers and hyphens (-)")
      .toString();
  }
}
