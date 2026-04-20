package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.shared.error.domain.Assert;

public record RemoveJavaAnnotationProcessor(DependencyId dependency) implements JavaBuildCommand {
  public RemoveJavaAnnotationProcessor {
    Assert.notNull("dependency", dependency);
  }
}
