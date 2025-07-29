package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import com.seed4j.shared.error.domain.GeneratorException;

class InvalidPomException extends GeneratorException {

  public InvalidPomException() {
    super(
      badRequest(MavenDependencyErrorKey.INVALID_POM).message(
        "Your pom.xml file is invalid, you have to define, at least, an artifact id for your project"
      )
    );
  }
}
