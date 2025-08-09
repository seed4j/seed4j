package com.seed4j.module.domain.mavenplugin;

import com.seed4j.module.domain.javabuild.command.*;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class SeedModuleMavenPlugins {

  private final Collection<MavenPlugin> pluginsManagement;
  private final Collection<MavenPlugin> plugins;

  private SeedModuleMavenPlugins(SeedModuleMavenPluginsBuilder<?> builder) {
    pluginsManagement = builder.pluginsManagement;
    plugins = builder.plugins;
  }

  public static <T> SeedModuleMavenPluginsBuilder<T> builder(T parentModuleBuilder) {
    return new SeedModuleMavenPluginsBuilder<>(parentModuleBuilder);
  }

  public JavaBuildCommands buildChanges(JavaDependenciesVersions versions, ProjectJavaDependencies projectJavaDependencies) {
    return buildChanges(versions, projectJavaDependencies, Optional.empty());
  }

  public JavaBuildCommands buildChanges(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectJavaDependencies,
    BuildProfileId buildProfile
  ) {
    Assert.notNull("buildProfile", buildProfile);
    return buildChanges(versions, projectJavaDependencies, Optional.of(buildProfile));
  }

  private JavaBuildCommands buildChanges(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectJavaDependencies,
    Optional<BuildProfileId> buildProfile
  ) {
    Assert.notNull("versions", versions);
    Assert.notNull("projectJavaDependencies", projectJavaDependencies);

    Stream<JavaBuildCommand> managementCommands = pluginsManagement
      .stream()
      .map(toAddMavenPlugin(versions, projectJavaDependencies, buildProfile, AddMavenPluginManagement::builder));
    Stream<JavaBuildCommand> pluginsCommands = plugins
      .stream()
      .map(toAddMavenPlugin(versions, projectJavaDependencies, buildProfile, AddDirectMavenPlugin::builder));

    return new JavaBuildCommands(Stream.concat(managementCommands, pluginsCommands).toList());
  }

  private static <C extends AddMavenPlugin> Function<MavenPlugin, C> toAddMavenPlugin(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectDependencies,
    Optional<BuildProfileId> buildProfile,
    Supplier<AddMavenPluginPluginBuilder<C>> builderFactory
  ) {
    return plugin -> {
      AddMavenPluginOptionalBuilder<C> commandBuilder = builderFactory.get().plugin(plugin);
      buildProfile.ifPresent(commandBuilder::buildProfile);
      plugin
        .dependencies()
        .stream()
        .flatMap(toDependencyVersion(versions, projectDependencies))
        .forEach(commandBuilder::addDependencyVersion);
      plugin.versionSlug().map(versions::get).ifPresent(commandBuilder::pluginVersion);
      return commandBuilder.build();
    };
  }

  private static Function<JavaDependency, Stream<JavaDependencyVersion>> toDependencyVersion(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectDependencies
  ) {
    return dependency -> dependency.version().flatMap(JavaDependency.toVersion(versions, projectDependencies)).stream();
  }

  public static final class SeedModuleMavenPluginsBuilder<T> {

    private final T parentModuleBuilder;
    private final Collection<MavenPlugin> pluginsManagement = new ArrayList<>();
    private final Collection<MavenPlugin> plugins = new ArrayList<>();

    private SeedModuleMavenPluginsBuilder(T parentModuleBuilder) {
      Assert.notNull("parentModuleBuilder", parentModuleBuilder);

      this.parentModuleBuilder = parentModuleBuilder;
    }

    public SeedModuleMavenPluginsBuilder<T> pluginManagement(MavenPlugin pluginManagement) {
      Assert.notNull("pluginManagement", pluginManagement);

      pluginsManagement.add(pluginManagement);

      return this;
    }

    public SeedModuleMavenPluginsBuilder<T> plugin(MavenPlugin plugin) {
      Assert.notNull("plugin", plugin);

      plugins.add(plugin);

      return this;
    }

    public T and() {
      return parentModuleBuilder;
    }

    public SeedModuleMavenPlugins build() {
      return new SeedModuleMavenPlugins(this);
    }
  }
}
