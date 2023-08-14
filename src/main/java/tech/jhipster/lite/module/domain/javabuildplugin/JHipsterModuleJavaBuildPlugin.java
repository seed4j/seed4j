package tech.jhipster.lite.module.domain.javabuildplugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.javabuild.command.AddBuildPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersions;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModuleJavaBuildPlugin {

  private final Collection<JavaBuildPlugin> pluginsManagement;
  private final Collection<JavaBuildPlugin> plugins;

  private JHipsterModuleJavaBuildPlugin(JHipsterModuleJavaBuildPluginBuilder builder) {
    pluginsManagement = builder.pluginsManagement;
    plugins = builder.plugins;
  }

  public static JHipsterModuleJavaBuildPluginBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleJavaBuildPluginBuilder(module);
  }

  public JavaBuildCommands buildChanges(JavaDependenciesVersions versions) {
    Assert.notNull("versions", versions);

    Stream<JavaBuildCommand> managementCommands = pluginsManagement.stream().flatMap(toCommands(versions, AddBuildPluginManagement::new));
    Stream<JavaBuildCommand> pluginsCommands = plugins.stream().flatMap(toCommands(versions, AddDirectJavaBuildPlugin::new));

    return new JavaBuildCommands(Stream.concat(managementCommands, pluginsCommands).toList());
  }

  private static Function<JavaBuildPlugin, Stream<JavaBuildCommand>> toCommands(
    JavaDependenciesVersions versions,
    Function<JavaBuildPlugin, JavaBuildCommand> addCommandFactory
  ) {
    return plugin -> {
      JavaBuildCommand addPluginCommand = addCommandFactory.apply(plugin);

      return plugin
        .versionSlug()
        .map(version -> Stream.of(new SetVersion(versions.get(version)), addPluginCommand))
        .orElseGet(() -> Stream.of(addPluginCommand));
    };
  }

  public static class JHipsterModuleJavaBuildPluginBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<JavaBuildPlugin> pluginsManagement = new ArrayList<>();
    private final Collection<JavaBuildPlugin> plugins = new ArrayList<>();

    private JHipsterModuleJavaBuildPluginBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleJavaBuildPluginBuilder pluginManagement(JavaBuildPlugin pluginManagement) {
      Assert.notNull("pluginManagement", pluginManagement);

      pluginsManagement.add(pluginManagement);

      return this;
    }

    public JHipsterModuleJavaBuildPluginBuilder plugin(JavaBuildPlugin plugin) {
      Assert.notNull("plugin", plugin);

      plugins.add(plugin);

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleJavaBuildPlugin build() {
      return new JHipsterModuleJavaBuildPlugin(this);
    }
  }
}
