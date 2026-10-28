package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.shared.error.domain.Assert;

public record SetVersion(JavaDependencyVersion version) implements JavaBuildCommand {
  public SetVersion {
    Assert.notNull("version", version);
  }

  public String property() {
    return version().slug().propertyName();
  }

  public String dependencyVersion() {
    return version().version().get();
  }
}
