package com.seed4j.module.domain.gradleplugin;

import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class GradleCommunityProfilePlugin implements GradleProfilePlugin {

  private final GradlePluginId id;
  private final GradlePluginDependency dependency;
  private final GradlePluginImports imports;
  private final Optional<GradlePluginConfiguration> configuration;
  private final Optional<VersionSlug> versionSlug;

  private GradleCommunityProfilePlugin(GradleCommunityProfilePluginBuilder builder) {
    Assert.notNull("id", builder.id);
    Assert.notNull("dependencyId", builder.dependency);

    id = builder.id;
    this.dependency = builder.dependency;
    imports = new GradlePluginImports(builder.imports);
    this.configuration = Optional.ofNullable(builder.configuration);
    this.versionSlug = Optional.ofNullable(builder.versionSlug);
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

  public GradlePluginDependency dependency() {
    return dependency;
  }

  public Optional<VersionSlug> versionSlug() {
    return versionSlug;
  }

  public static GradleCommunityProfilePluginIdBuilder builder() {
    return new GradleCommunityProfilePluginBuilder();
  }

  private static final class GradleCommunityProfilePluginBuilder
    implements
      GradleCommunityProfilePluginIdBuilder, GradleCommunityProfilePluginDependencyBuilder, GradleCommunityProfilePluginOptionalBuilder {

    private @Nullable GradlePluginId id;
    private @Nullable GradlePluginDependency dependency;
    private final Collection<BuildGradleImport> imports = new ArrayList<>();
    private @Nullable GradlePluginConfiguration configuration;
    private @Nullable VersionSlug versionSlug;

    @Override
    public GradleCommunityProfilePluginDependencyBuilder id(GradlePluginId id) {
      this.id = id;

      return this;
    }

    @Override
    public GradleCommunityProfilePluginOptionalBuilder dependency(GradlePluginDependency dependency) {
      this.dependency = dependency;

      return this;
    }

    @Override
    public GradleCommunityProfilePluginOptionalBuilder withBuildGradleImport(BuildGradleImport gradleImport) {
      imports.add(gradleImport);

      return this;
    }

    @Override
    public GradleCommunityProfilePluginOptionalBuilder configuration(GradlePluginConfiguration configuration) {
      this.configuration = configuration;

      return this;
    }

    @Override
    public GradleCommunityProfilePluginOptionalBuilder versionSlug(VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public GradleCommunityProfilePlugin build() {
      return new GradleCommunityProfilePlugin(this);
    }
  }

  public interface GradleCommunityProfilePluginIdBuilder {
    GradleCommunityProfilePluginDependencyBuilder id(GradlePluginId id);

    default GradleCommunityProfilePluginDependencyBuilder id(String id) {
      return id(new GradlePluginId(id));
    }
  }

  public interface GradleCommunityProfilePluginDependencyBuilder {
    GradleCommunityProfilePluginOptionalBuilder dependency(GradlePluginDependency dependency);

    default GradleCommunityProfilePluginOptionalBuilder dependency(GroupId groupId, ArtifactId artifactId) {
      return dependency(new GradlePluginDependency(groupId, artifactId));
    }
  }

  public interface GradleCommunityProfilePluginOptionalBuilder {
    GradleCommunityProfilePluginOptionalBuilder withBuildGradleImport(BuildGradleImport gradleImport);

    default GradleCommunityProfilePluginOptionalBuilder withBuildGradleImport(String gradleImport) {
      return withBuildGradleImport(new BuildGradleImport(gradleImport));
    }

    GradleCommunityProfilePluginOptionalBuilder configuration(GradlePluginConfiguration configuration);

    default GradleCommunityProfilePluginOptionalBuilder configuration(String configuration) {
      return configuration(new GradlePluginConfiguration(configuration));
    }

    GradleCommunityProfilePluginOptionalBuilder versionSlug(VersionSlug versionSlug);

    default GradleCommunityProfilePluginOptionalBuilder versionSlug(String versionSlug) {
      return versionSlug(new VersionSlug(versionSlug));
    }

    GradleCommunityProfilePlugin build();
  }
}
