package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.shared.error.domain.Assert;

public record RemoveJavaDependencyManagement(DependencyId dependency) implements JavaBuildCommand {
  public RemoveJavaDependencyManagement {
    Assert.notNull("dependency", dependency);
  }

  @Override
  public JavaBuildCommandType type() {
    return JavaBuildCommandType.REMOVE_DEPENDENCY_MANAGEMENT;
  }
}
