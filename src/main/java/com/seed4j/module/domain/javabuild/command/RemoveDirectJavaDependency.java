package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;

public record RemoveDirectJavaDependency(DependencyId dependency, Optional<BuildProfileId> buildProfile) implements JavaBuildCommand {
  public RemoveDirectJavaDependency {
    Assert.notNull("dependency", dependency);
    Assert.notNull("buildProfile", buildProfile);
  }

  public RemoveDirectJavaDependency(DependencyId dependency) {
    this(dependency, Optional.empty());
  }

  public RemoveDirectJavaDependency(DependencyId dependency, BuildProfileId buildProfile) {
    this(dependency, Optional.of(buildProfile));
  }
}
