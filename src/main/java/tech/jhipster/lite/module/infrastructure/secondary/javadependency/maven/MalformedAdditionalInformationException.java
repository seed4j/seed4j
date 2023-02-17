package tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven;

import tech.jhipster.lite.error.domain.GeneratorException;

class MalformedAdditionalInformationException extends GeneratorException {

  public MalformedAdditionalInformationException(Throwable cause) {
    super(
      internalServerError(MavenDependencyErrorKey.MALFORMED_ADDITIONAL_INFORMATION)
        .message("Malformed XML additional elements for plugin")
        .cause(cause)
    );
  }
}
