package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.GeneratorException;

class MissingPackageJsonException extends GeneratorException {

  public MissingPackageJsonException(JHipsterProjectFolder folder) {
    super(badRequest(ModuleSecondaryErrorKey.MISSING_PACKAGE_JSON).message(buildMessage(folder)).addParameter("folder", folder.get()));
  }

  private static String buildMessage(JHipsterProjectFolder folder) {
    return "package.json is missing in %s, can't apply module".formatted(folder.get());
  }
}
