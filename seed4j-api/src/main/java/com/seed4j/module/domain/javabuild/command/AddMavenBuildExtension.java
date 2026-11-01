package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javabuild.MavenBuildExtension;
import com.seed4j.shared.error.domain.Assert;

public record AddMavenBuildExtension(MavenBuildExtension buildExtension) implements JavaBuildCommand {
  public AddMavenBuildExtension {
    Assert.notNull("buildExtension", buildExtension);
  }
}
