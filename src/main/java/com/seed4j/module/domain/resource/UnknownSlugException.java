package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.shared.error.domain.GeneratorException;

class UnknownSlugException extends GeneratorException {

  public UnknownSlugException(SeedModuleSlug slug) {
    super(internalServerError(ResourceErrorKey.UNKNOWN_SLUG).message(buildMessage(slug)).addParameter("slug", slug.get()));
  }

  private static String buildMessage(SeedModuleSlug slug) {
    return "Module %s does not exist".formatted(slug.get());
  }
}
