package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.shared.error.domain.GeneratorException;

class UnknownSlugException extends GeneratorException {

  public UnknownSlugException(Seed4JModuleSlug slug) {
    super(internalServerError(ResourceErrorKey.UNKNOWN_SLUG).message(buildMessage(slug)).addParameter("slug", slug.get()));
  }

  private static String buildMessage(Seed4JModuleSlug slug) {
    return "Module %s does not exist".formatted(slug.get());
  }
}
