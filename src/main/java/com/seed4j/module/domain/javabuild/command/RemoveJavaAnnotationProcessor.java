package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record RemoveJavaAnnotationProcessor(DependencyId dependency) implements JavaBuildCommand {
  public RemoveJavaAnnotationProcessor {
    Assert.notNull("dependency", dependency);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Feature not yet supported")
  public RemoveJavaAnnotationProcessor(DependencyId dependency, BuildProfileId buildProfile) {
    this(dependency);
    throw new UnsupportedOperationException("Build profile is not yet supported for annotation processor dependencies.");
  }
}
