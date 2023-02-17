package tech.jhipster.lite.module.infrastructure.secondary.javadependency.gradle;

import tech.jhipster.lite.error.domain.GeneratorException;

class InvalidTomlVersionCatalogException extends GeneratorException {

  public InvalidTomlVersionCatalogException(Throwable cause) {
    super(
      badRequest(GradleDependencyErrorKey.INVALID_TOML_VERSION_CATALOG)
        .message("Your gradle/libs.versions.toml file is invalid")
        .cause(cause)
    );
  }
}
