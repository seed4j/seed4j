package com.seed4j.module.domain.gradleplugin;

import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class GradleCorePlugin implements GradleMainBuildPlugin, GradleProfilePlugin {

  private final GradlePluginId id;
  private final GradlePluginImports imports;
  private final Optional<GradlePluginConfiguration> configuration;
  private final Optional<VersionSlug> toolVersionSlug;

  private GradleCorePlugin(GradleCorePluginBuilder builder) {
    Assert.notNull("id", builder.id);
    id = builder.id;
    imports = new GradlePluginImports(builder.imports);
    configuration = Optional.ofNullable(builder.configuration);
    toolVersionSlug = Optional.ofNullable(builder.toolVersionSlug);
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

  public Optional<VersionSlug> toolVersionSlug() {
    return toolVersionSlug;
  }

  public static GradleCorePluginIdBuilder builder() {
    return new GradleCorePluginBuilder();
  }

  private static final class GradleCorePluginBuilder implements GradleCorePluginIdBuilder, GradleCorePluginOptionalBuilder {

    private GradlePluginId id;
    private final Collection<BuildGradleImport> imports = new ArrayList<>();
    private GradlePluginConfiguration configuration;
    private VersionSlug toolVersionSlug;

    @Override
    public GradleCorePluginOptionalBuilder id(GradlePluginId id) {
      this.id = id;

      return this;
    }

    @Override
    public GradleCorePluginOptionalBuilder withBuildGradleImport(BuildGradleImport gradleImport) {
      imports.add(gradleImport);

      return this;
    }

    @Override
    public GradleCorePluginOptionalBuilder configuration(GradlePluginConfiguration configuration) {
      this.configuration = configuration;

      return this;
    }

    @Override
    public GradleCorePluginOptionalBuilder toolVersionSlug(VersionSlug versionSlug) {
      this.toolVersionSlug = versionSlug;

      return this;
    }

    @Override
    public GradleCorePlugin build() {
      return new GradleCorePlugin(this);
    }
  }

  public interface GradleCorePluginIdBuilder {
    GradleCorePluginOptionalBuilder id(GradlePluginId id);

    default GradleCorePluginOptionalBuilder id(String id) {
      return id(new GradlePluginId(id));
    }
  }

  public interface GradleCorePluginOptionalBuilder {
    GradleCorePluginOptionalBuilder withBuildGradleImport(BuildGradleImport gradleImport);

    default GradleCorePluginOptionalBuilder withBuildGradleImport(String gradleImport) {
      return withBuildGradleImport(new BuildGradleImport(gradleImport));
    }

    GradleCorePluginOptionalBuilder configuration(GradlePluginConfiguration configuration);

    default GradleCorePluginOptionalBuilder configuration(String configuration) {
      return configuration(new GradlePluginConfiguration(configuration));
    }

    GradleCorePluginOptionalBuilder toolVersionSlug(VersionSlug versionSlug);

    default GradleCorePluginOptionalBuilder toolVersionSlug(String versionSlug) {
      return toolVersionSlug(new VersionSlug(versionSlug));
    }

    GradleCorePlugin build();
  }
}
