package tech.jhipster.lite.module.infrastructure.secondary.git;

import tech.jhipster.lite.shared.error.domain.GeneratorException;

class GitInitException extends GeneratorException {

  public GitInitException(String message, Throwable cause) {
    super(internalServerError(GitErrorKey.INIT_ERROR).message(message).cause(cause));
  }
}
