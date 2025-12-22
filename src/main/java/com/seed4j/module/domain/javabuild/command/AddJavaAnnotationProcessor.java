package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record AddJavaAnnotationProcessor(JavaDependency dependency) implements JavaBuildCommand, AddJavaDependency {
  public AddJavaAnnotationProcessor {
    Assert.notNull("dependency", dependency);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Feature not yet supported")
  public AddJavaAnnotationProcessor(JavaDependency javaDependency, BuildProfileId buildProfileId) {
    this(javaDependency);
    throw new UnsupportedOperationException("Build profile is not yet supported for annotation processor dependencies.");
  }
}
