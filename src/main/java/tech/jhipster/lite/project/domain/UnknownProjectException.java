package tech.jhipster.lite.project.domain;

import tech.jhipster.lite.shared.error.domain.GeneratorException;

public class UnknownProjectException extends GeneratorException {

  public UnknownProjectException() {
    super(badRequest(ProjectErrorKey.UNKOWN_PROJECT).message("A user tried to download an unknown project"));
  }
}
