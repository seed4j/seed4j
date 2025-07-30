package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;

public record AddJavaDependencyManagement(JavaDependency dependency, Optional<BuildProfileId> buildProfile) implements
  JavaBuildCommand, AddJavaDependency {
  public AddJavaDependencyManagement {
    Assert.notNull("dependency", dependency);
    Assert.notNull("buildProfile", buildProfile);
  }

  public AddJavaDependencyManagement(JavaDependency dependency) {
    this(dependency, Optional.empty());
  }

  public AddJavaDependencyManagement(JavaDependency dependency, BuildProfileId buildProfile) {
    this(dependency, Optional.of(buildProfile));
  }
}
