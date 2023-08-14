package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.shared.error.domain.Assert;

public record RemoveDirectJavaDependency(DependencyId dependency) implements JavaBuildCommand {
  public RemoveDirectJavaDependency {
    Assert.notNull("dependency", dependency);
  }

  @Override
  public JavaBuildCommandType type() {
    return JavaBuildCommandType.REMOVE_DEPENDENCY;
  }
}
