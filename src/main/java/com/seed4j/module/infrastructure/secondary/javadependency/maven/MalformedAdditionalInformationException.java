package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import com.seed4j.shared.error.domain.GeneratorException;

class MalformedAdditionalInformationException extends GeneratorException {

  public MalformedAdditionalInformationException(Throwable cause) {
    super(
      internalServerError(MavenDependencyErrorKey.MALFORMED_ADDITIONAL_INFORMATION)
        .message("Malformed XML additional elements for plugin")
        .cause(cause)
    );
  }
}
