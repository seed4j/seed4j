package com.seed4j.module.infrastructure.secondary.git;

import com.seed4j.shared.error.domain.GeneratorException;

class GitCommitException extends GeneratorException {

  public GitCommitException(String message, Throwable cause) {
    super(internalServerError(GitErrorKey.COMMIT_ERROR).message(message).cause(cause));
  }

  public GitCommitException(String message) {
    super(internalServerError(GitErrorKey.COMMIT_ERROR).message(message));
  }
}
