package com.seed4j.project.infrastructure.secondary;

import com.seed4j.shared.error.domain.GeneratorException;

class ProjectZippingException extends GeneratorException {

  public ProjectZippingException(String message) {
    super(internalServerError(ProjectErrorKey.ZIPPING_ERROR).message(message));
  }

  public ProjectZippingException(Throwable cause) {
    super(internalServerError(ProjectErrorKey.ZIPPING_ERROR).message("Error creating zip file").cause(cause));
  }
}
