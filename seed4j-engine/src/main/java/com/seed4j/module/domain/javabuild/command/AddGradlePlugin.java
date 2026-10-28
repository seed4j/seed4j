package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.gradleplugin.GradlePlugin;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class AddGradlePlugin implements JavaBuildCommand {

  private final GradlePlugin plugin;
  private final Optional<JavaDependencyVersion> pluginVersion;
  private final Optional<JavaDependencyVersion> toolVersion;
  private final Optional<BuildProfileId> buildProfile;

  private AddGradlePlugin(AddGradlePlugin.AddGradlePluginBuilder builder) {
    Assert.notNull("plugin", builder.plugin);
    this.plugin = builder.plugin;
    this.pluginVersion = Optional.ofNullable(builder.pluginVersion);
    this.toolVersion = Optional.ofNullable(builder.toolVersion);
    this.buildProfile = Optional.ofNullable(builder.buildProfile);
  }

  public GradlePlugin plugin() {
    return plugin;
  }

  public Optional<JavaDependencyVersion> pluginVersion() {
    return pluginVersion;
  }

  public Optional<JavaDependencyVersion> toolVersion() {
    return toolVersion;
  }

  public Optional<BuildProfileId> buildProfile() {
    return buildProfile;
  }

  public static AddGradlePluginPluginBuilder builder() {
    return new AddGradlePluginBuilder();
  }

  private static final class AddGradlePluginBuilder implements AddGradlePluginPluginBuilder, AddGradlePluginOptionalBuilder {

    private @Nullable GradlePlugin plugin;
    private @Nullable JavaDependencyVersion pluginVersion;
    private @Nullable JavaDependencyVersion toolVersion;
    private @Nullable BuildProfileId buildProfile;

    @Override
    public AddGradlePluginBuilder plugin(GradlePlugin plugin) {
      this.plugin = plugin;
      return this;
    }

    @Override
    public AddGradlePluginOptionalBuilder pluginVersion(JavaDependencyVersion pluginVersion) {
      this.pluginVersion = pluginVersion;
      return this;
    }

    @Override
    public AddGradlePluginOptionalBuilder toolVersion(JavaDependencyVersion toolVersion) {
      this.toolVersion = toolVersion;
      return this;
    }

    @Override
    public AddGradlePluginOptionalBuilder buildProfile(BuildProfileId buildProfile) {
      this.buildProfile = buildProfile;
      return this;
    }

    @Override
    public AddGradlePlugin build() {
      return new AddGradlePlugin(this);
    }
  }

  public interface AddGradlePluginPluginBuilder {
    AddGradlePluginOptionalBuilder plugin(GradlePlugin plugin);
  }

  public interface AddGradlePluginOptionalBuilder {
    AddGradlePluginOptionalBuilder pluginVersion(JavaDependencyVersion version);

    AddGradlePluginOptionalBuilder toolVersion(JavaDependencyVersion version);

    AddGradlePluginOptionalBuilder buildProfile(BuildProfileId buildProfile);

    AddGradlePlugin build();
  }
}
