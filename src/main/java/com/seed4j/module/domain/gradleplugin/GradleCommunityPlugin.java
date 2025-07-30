package com.seed4j.module.domain.gradleplugin;

import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class GradleCommunityPlugin implements GradleMainBuildPlugin {

  private final GradlePluginId id;
  private final GradlePluginImports imports;
  private final Optional<GradlePluginConfiguration> configuration;
  private final Optional<VersionSlug> versionSlug;
  private final Optional<GradlePluginSlug> pluginSlug;

  private GradleCommunityPlugin(GradleCommunityPluginBuilder builder) {
    Assert.notNull("id", builder.id);
    id = builder.id;
    imports = new GradlePluginImports(builder.imports);
    configuration = Optional.ofNullable(builder.configuration);
    versionSlug = Optional.ofNullable(builder.versionSlug);
    pluginSlug = Optional.ofNullable(builder.pluginSlug);
  }

  @Override
  public GradlePluginId id() {
    return id;
  }

  @Override
  public GradlePluginImports imports() {
    return imports;
  }

  @Override
  public Optional<GradlePluginConfiguration> configuration() {
    return configuration;
  }

  public Optional<VersionSlug> versionSlug() {
    return versionSlug;
  }

  public Optional<GradlePluginSlug> pluginSlug() {
    return pluginSlug;
  }

  public static GradleCommunityPluginIdBuilder builder() {
    return new GradleCommunityPluginBuilder();
  }

  private static final class GradleCommunityPluginBuilder implements GradleCommunityPluginIdBuilder, GradleCommunityPluginOptionalBuilder {

    private GradlePluginId id;
    private final Collection<BuildGradleImport> imports = new ArrayList<>();
    private GradlePluginConfiguration configuration;
    private VersionSlug versionSlug;
    private GradlePluginSlug pluginSlug;

    @Override
    public GradleCommunityPluginOptionalBuilder id(GradlePluginId id) {
      this.id = id;

      return this;
    }

    @Override
    public GradleCommunityPluginOptionalBuilder withBuildGradleImport(BuildGradleImport gradleImport) {
      imports.add(gradleImport);

      return this;
    }

    @Override
    public GradleCommunityPluginOptionalBuilder configuration(GradlePluginConfiguration configuration) {
      this.configuration = configuration;

      return this;
    }

    @Override
    public GradleCommunityPluginOptionalBuilder versionSlug(VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public GradleCommunityPluginOptionalBuilder pluginSlug(GradlePluginSlug pluginSlug) {
      this.pluginSlug = pluginSlug;

      return this;
    }

    @Override
    public GradleCommunityPlugin build() {
      return new GradleCommunityPlugin(this);
    }
  }

  public interface GradleCommunityPluginIdBuilder {
    GradleCommunityPluginOptionalBuilder id(GradlePluginId id);

    default GradleCommunityPluginOptionalBuilder id(String id) {
      return id(new GradlePluginId(id));
    }
  }

  public interface GradleCommunityPluginOptionalBuilder {
    GradleCommunityPluginOptionalBuilder withBuildGradleImport(BuildGradleImport gradleImport);

    default GradleCommunityPluginOptionalBuilder withBuildGradleImport(String gradleImport) {
      return withBuildGradleImport(new BuildGradleImport(gradleImport));
    }

    GradleCommunityPluginOptionalBuilder configuration(GradlePluginConfiguration configuration);

    default GradleCommunityPluginOptionalBuilder configuration(String configuration) {
      return configuration(new GradlePluginConfiguration(configuration));
    }

    GradleCommunityPluginOptionalBuilder versionSlug(VersionSlug versionSlug);

    default GradleCommunityPluginOptionalBuilder versionSlug(String versionSlug) {
      return versionSlug(new VersionSlug(versionSlug));
    }

    GradleCommunityPluginOptionalBuilder pluginSlug(GradlePluginSlug pluginSlug);

    default GradleCommunityPluginOptionalBuilder pluginSlug(String pluginSlug) {
      return pluginSlug(new GradlePluginSlug(pluginSlug));
    }

    GradleCommunityPlugin build();
  }
}
