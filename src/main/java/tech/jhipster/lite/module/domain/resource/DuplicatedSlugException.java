package tech.jhipster.lite.module.domain.resource;

import tech.jhipster.lite.shared.error.domain.GeneratorException;

class DuplicatedSlugException extends GeneratorException {

  public DuplicatedSlugException() {
    super(internalServerError(ResourceErrorKey.DUPLICATED_SLUG).message("Found a duplicated module slug, ensure that slugs are unique"));
  }
}
