package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Optional;

public final class AddDirectMavenPlugin implements JavaBuildCommand, AddMavenPlugin {

  private final MavenPlugin plugin;
  private final Optional<JavaDependencyVersion> pluginVersion;
  private final Optional<BuildProfileId> buildProfile;
  private final Collection<JavaDependencyVersion> dependenciesVersions;

  private AddDirectMavenPlugin(AddMavenPluginBuilder<?> builder) {
    Assert.notNull("plugin", builder.plugin());
    Assert.notNull("dependenciesVersions", builder.dependenciesVersions());
    this.plugin = builder.plugin();
    this.dependenciesVersions = builder.dependenciesVersions();
    this.pluginVersion = Optional.ofNullable(builder.pluginVersion());
    this.buildProfile = Optional.ofNullable(builder.buildProfile());
  }

  @Override
  public MavenPlugin plugin() {
    return plugin;
  }

  public Optional<BuildProfileId> buildProfile() {
    return buildProfile;
  }

  @Override
  public Optional<JavaDependencyVersion> pluginVersion() {
    return pluginVersion;
  }

  public Collection<JavaDependencyVersion> dependenciesVersions() {
    return dependenciesVersions;
  }

  public static AddMavenPluginPluginBuilder<AddDirectMavenPlugin> builder() {
    return new AddMavenPluginBuilder<>(AddDirectMavenPlugin::new);
  }
}
