package com.seed4j.module.infrastructure.secondary.javadependency.gradle;

import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.shared.error.domain.GeneratorException;

class MissingGradleProfileException extends GeneratorException {

  public MissingGradleProfileException(BuildProfileId profileId) {
    super(
      badRequest(GradleDependencyErrorKey.MISSING_PROFILE).message(
        "Your gradle project does not contain any profile-%s.gradle.kts precompiled script plugin".formatted(profileId)
      )
    );
  }
}
