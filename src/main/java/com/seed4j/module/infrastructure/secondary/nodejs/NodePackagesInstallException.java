package com.seed4j.module.infrastructure.secondary.nodejs;

import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@ExcludeFromGeneratedCodeCoverage
class NodePackagesInstallException extends GeneratorException {

  public NodePackagesInstallException(String message) {
    super(internalServerError(NodeErrorKey.INSTALL_ERROR).message(message));
  }

  public NodePackagesInstallException(String message, Throwable cause) {
    super(internalServerError(NodeErrorKey.INSTALL_ERROR).message(message).cause(cause));
  }
}
