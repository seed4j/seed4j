package com.seed4j.project.domain;

import com.seed4j.shared.error.domain.GeneratorException;

public class UnknownProjectException extends GeneratorException {

  public UnknownProjectException() {
    super(badRequest(ProjectErrorKey.UNKNOWN_PROJECT).message("A user tried to download an unknown project"));
  }
}
