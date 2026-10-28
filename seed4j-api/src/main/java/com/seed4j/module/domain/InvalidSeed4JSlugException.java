package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.GeneratorException;

class InvalidSeed4JSlugException extends GeneratorException {

  public InvalidSeed4JSlugException(String slug) {
    super(internalServerError(ModuleErrorKey.INVALID_SLUG).message(buildMessage(slug)).addParameter("slug", slug));
  }

  private static String buildMessage(String slug) {
    return new StringBuilder()
      .append("The slug \"")
      .append(slug)
      .append("\" is invalid (blank, bad format, ...). Slug should be only lower case letters, numbers and hyphens (-)")
      .toString();
  }
}
