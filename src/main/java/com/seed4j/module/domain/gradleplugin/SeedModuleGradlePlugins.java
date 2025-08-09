package com.seed4j.module.domain.gradleplugin;

import com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import com.seed4j.module.domain.javabuild.command.AddGradlePlugin;
import com.seed4j.module.domain.javabuild.command.AddGradlePlugin.AddGradlePluginOptionalBuilder;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public final class SeedModuleGradlePlugins {

  private final Collection<GradleMainBuildPlugin> plugins;

  private SeedModuleGradlePlugins(SeedModuleGradlePluginBuilder builder) {
    Assert.notNull("plugins", builder.plugins);
    plugins = builder.plugins;
  }

  public static SeedModuleGradlePluginBuilder builder(SeedModuleBuilder module) {
    return new SeedModuleGradlePluginBuilder(module);
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

  public static final class SeedModuleGradlePluginBuilder {

    private final SeedModuleBuilder module;
    private final Collection<GradleMainBuildPlugin> plugins = new ArrayList<>();

    private SeedModuleGradlePluginBuilder(SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public SeedModuleGradlePluginBuilder plugin(GradleMainBuildPlugin plugin) {
      Assert.notNull("plugin", plugin);

      plugins.add(plugin);

      return this;
    }

    public SeedModuleBuilder and() {
      return module;
    }

    public SeedModuleGradlePlugins build() {
      return new SeedModuleGradlePlugins(this);
    }
  }
}
