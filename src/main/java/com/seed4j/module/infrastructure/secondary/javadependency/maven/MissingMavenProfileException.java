package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.shared.error.domain.GeneratorException;

class MissingMavenProfileException extends GeneratorException {

  public MissingMavenProfileException(BuildProfileId profileId) {
    super(badRequest(MavenDependencyErrorKey.MISSING_PROFILE).message("Your pom.xml file does not contain the profile " + profileId));
  }
}
