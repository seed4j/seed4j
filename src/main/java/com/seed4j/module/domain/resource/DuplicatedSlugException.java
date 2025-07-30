package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.GeneratorException;

class DuplicatedSlugException extends GeneratorException {

  public DuplicatedSlugException() {
    super(internalServerError(ResourceErrorKey.DUPLICATED_SLUG).message("Found a duplicated module slug, ensure that slugs are unique"));
  }
}
