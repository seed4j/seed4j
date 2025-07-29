package com.seed4j.module.infrastructure.secondary.git;

import com.seed4j.shared.error.domain.GeneratorException;

class GitInitException extends GeneratorException {

  public GitInitException(String message, Throwable cause) {
    super(internalServerError(GitErrorKey.INIT_ERROR).message(message).cause(cause));
  }
}
