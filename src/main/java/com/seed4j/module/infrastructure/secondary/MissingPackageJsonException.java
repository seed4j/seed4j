package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.shared.error.domain.GeneratorException;

class MissingPackageJsonException extends GeneratorException {

  public MissingPackageJsonException(SeedProjectFolder folder) {
    super(badRequest(ModuleSecondaryErrorKey.MISSING_PACKAGE_JSON).message(buildMessage(folder)).addParameter("folder", folder.get()));
  }

  private static String buildMessage(SeedProjectFolder folder) {
    return "package.json is missing in %s, can't apply module".formatted(folder.get());
  }
}
