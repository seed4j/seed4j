package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.function.Function;
import org.jspecify.annotations.Nullable;

class AddMavenPluginBuilder<T extends AddMavenPlugin> implements AddMavenPluginPluginBuilder<T>, AddMavenPluginOptionalBuilder<T> {

  private final Function<AddMavenPluginBuilder<T>, T> factory;
  private @Nullable MavenPlugin plugin;
  private @Nullable JavaDependencyVersion pluginVersion;
  private @Nullable BuildProfileId buildProfile;
  private final Collection<JavaDependencyVersion> dependenciesVersions = new LinkedHashSet<>();

  AddMavenPluginBuilder(Function<AddMavenPluginBuilder<T>, T> factory) {
    this.factory = factory;
  }

  public @Nullable MavenPlugin plugin() {
    return plugin;
  }

  public @Nullable JavaDependencyVersion pluginVersion() {
    return pluginVersion;
  }

  public @Nullable BuildProfileId buildProfile() {
    return buildProfile;
  }

  public Collection<JavaDependencyVersion> dependenciesVersions() {
    return dependenciesVersions;
  }

  @Override
  public AddMavenPluginBuilder<T> plugin(MavenPlugin plugin) {
    this.plugin = plugin;
    return this;
  }

  @Override
  public AddMavenPluginOptionalBuilder<T> pluginVersion(JavaDependencyVersion pluginVersion) {
    this.pluginVersion = pluginVersion;
    return this;
  }

  @Override
  public AddMavenPluginOptionalBuilder<T> buildProfile(BuildProfileId buildProfile) {
    this.buildProfile = buildProfile;
    return this;
  }

  @Override
  public AddMavenPluginOptionalBuilder<T> addDependencyVersion(JavaDependencyVersion dependencyVersion) {
    this.dependenciesVersions.add(dependencyVersion);
    return this;
  }

  @Override
  public T build() {
    return factory.apply(this);
  }
}
