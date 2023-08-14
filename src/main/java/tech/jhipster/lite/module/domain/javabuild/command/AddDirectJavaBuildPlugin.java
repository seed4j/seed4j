package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.shared.error.domain.Assert;

public record AddDirectJavaBuildPlugin(JavaBuildPlugin javaBuildPlugin) implements JavaBuildCommand, AddJavaBuildPlugin {
  public AddDirectJavaBuildPlugin {
    Assert.notNull("javaBuildPlugin", javaBuildPlugin);
  }

  @Override
  public JavaBuildCommandType type() {
    return JavaBuildCommandType.ADD_DIRECT_JAVA_BUILD_PLUGIN;
  }
}
