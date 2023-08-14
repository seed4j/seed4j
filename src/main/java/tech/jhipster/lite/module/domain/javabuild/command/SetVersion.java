package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.shared.error.domain.Assert;

public record SetVersion(JavaDependencyVersion version) implements JavaBuildCommand {
  public SetVersion {
    Assert.notNull("version", version);
  }

  @Override
  public JavaBuildCommandType type() {
    return JavaBuildCommandType.SET_VERSION;
  }

  public String property() {
    return version().slug().propertyName();
  }

  public String dependencyVersion() {
    return version().version().get();
  }
}
