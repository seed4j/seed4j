package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.shared.error.domain.Assert;

public record AddBuildPluginManagement(JavaBuildPlugin javaBuildPlugin) implements JavaBuildCommand, AddJavaBuildPlugin {
  public AddBuildPluginManagement {
    Assert.notNull("javaBuildPlugin", javaBuildPlugin);
  }

  @Override
  public JavaBuildCommandType type() {
    return JavaBuildCommandType.ADD_JAVA_BUILD_PLUGIN_MANAGEMENT;
  }
}
