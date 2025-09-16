package com.seed4j.module.domain.gradleplugin;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.javabuild.command.AddGradlePlugin;
import com.seed4j.module.domain.javabuild.command.AddGradlePlugin.AddGradlePluginOptionalBuilder;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public final class Seed4JModuleGradlePlugins {

  private final Collection<GradleMainBuildPlugin> plugins;

  private Seed4JModuleGradlePlugins(Seed4JModuleGradlePluginBuilder builder) {
    Assert.notNull("plugins", builder.plugins);
    plugins = builder.plugins;
  }

  public static Seed4JModuleGradlePluginBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleGradlePluginBuilder(module);
  }

  public JavaBuildCommands buildChanges(JavaDependenciesVersions versions) {
    Assert.notNull("versions", versions);

    return new JavaBuildCommands(plugins.stream().map(toCommands(versions)).toList());
  }

  private Function<GradleMainBuildPlugin, JavaBuildCommand> toCommands(JavaDependenciesVersions versions) {
    return plugin ->
      switch (plugin) {
        case GradleCorePlugin corePlugin -> mapCorePlugin(corePlugin, versions);
        case GradleCommunityPlugin gradleCommunityPlugin -> mapCommunityPlugin(gradleCommunityPlugin, versions);
      };
  }

  private JavaBuildCommand mapCorePlugin(GradleCorePlugin plugin, JavaDependenciesVersions versions) {
    AddGradlePluginOptionalBuilder commandBuilder = AddGradlePlugin.builder().plugin(plugin);
    plugin.toolVersionSlug().map(versions::get).ifPresent(commandBuilder::toolVersion);
    return commandBuilder.build();
  }

  private JavaBuildCommand mapCommunityPlugin(GradleCommunityPlugin plugin, JavaDependenciesVersions versions) {
    AddGradlePluginOptionalBuilder commandBuilder = AddGradlePlugin.builder().plugin(plugin);
    plugin.versionSlug().map(versions::get).ifPresent(commandBuilder::pluginVersion);
    return commandBuilder.build();
  }

  public static final class Seed4JModuleGradlePluginBuilder {

    private final Seed4JModuleBuilder module;
    private final Collection<GradleMainBuildPlugin> plugins = new ArrayList<>();

    private Seed4JModuleGradlePluginBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleGradlePluginBuilder plugin(GradleMainBuildPlugin plugin) {
      Assert.notNull("plugin", plugin);

      plugins.add(plugin);

      return this;
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModuleGradlePlugins build() {
      return new Seed4JModuleGradlePlugins(this);
    }
  }
}
