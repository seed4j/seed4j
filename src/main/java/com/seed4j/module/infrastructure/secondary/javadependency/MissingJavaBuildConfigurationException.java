package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.GeneratorException;

class MissingJavaBuildConfigurationException extends GeneratorException {

  public MissingJavaBuildConfigurationException(JHipsterProjectFolder folder) {
    super(
      badRequest(JavaDependencyErrorKey.MISSING_BUILD_CONFIGURATION).message(buildMessage(folder)).addParameter("folder", folder.get())
    );
  }

  private static String buildMessage(JHipsterProjectFolder folder) {
    return "Can't find any java build tool configuration in %s".formatted(folder.get());
  }
}
