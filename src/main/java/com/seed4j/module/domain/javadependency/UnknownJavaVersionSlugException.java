package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.shared.error.domain.GeneratorException;

class UnknownJavaVersionSlugException extends GeneratorException {

  public UnknownJavaVersionSlugException(VersionSlug slug) {
    super(
      internalServerError(JavaDependencyErrorKey.UNKNOWN_VERSION)
        .message(buildMessage(slug))
        .addParameter("versionSlug", slug.propertyName())
    );
  }

  private static String buildMessage(VersionSlug slug) {
    return new StringBuilder()
      .append("Can't find property ")
      .append(slug.propertyName())
      .append(", forgot to add it in \"src/main/resources/generator/dependencies/pom.xml\"?")
      .toString();
  }
}
