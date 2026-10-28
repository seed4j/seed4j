package com.seed4j.module.domain.javabuild.command;

import static org.apache.commons.lang3.builder.ToStringStyle.*;

import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Collection;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class AddMavenPluginManagement implements JavaBuildCommand, AddMavenPlugin {

  private final MavenPlugin plugin;
  private final Optional<JavaDependencyVersion> pluginVersion;
  private final Optional<BuildProfileId> buildProfile;
  private final Collection<JavaDependencyVersion> dependenciesVersions;

  private AddMavenPluginManagement(AddMavenPluginBuilder<?> builder) {
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

  @Override
  public Optional<JavaDependencyVersion> pluginVersion() {
    return pluginVersion;
  }

  public Optional<BuildProfileId> buildProfile() {
    return buildProfile;
  }

  public Collection<JavaDependencyVersion> dependenciesVersions() {
    return dependenciesVersions;
  }

  public static AddMavenPluginPluginBuilder<AddMavenPluginManagement> builder() {
    return new AddMavenPluginBuilder<>(AddMavenPluginManagement::new);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this, SHORT_PREFIX_STYLE)
      .append("plugin", plugin)
      .append("pluginVersion", pluginVersion.map(JavaDependencyVersion::toString).orElse(""))
      .append("dependenciesVersions", pluginVersion.map(JavaDependencyVersion::toString).orElse(""));
    return builder.toString();
  }
}
