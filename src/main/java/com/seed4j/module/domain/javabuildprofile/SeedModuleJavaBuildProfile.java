package com.seed4j.module.domain.javabuildprofile;

import com.seed4j.module.domain.buildproperties.SeedModuleBuildProperties;
import com.seed4j.module.domain.buildproperties.SeedModuleBuildProperties.SeedModuleBuildPropertiesBuilder;
import com.seed4j.module.domain.javabuildprofile.BuildProfileActivation.BuildProfileActivationBuilder;
import com.seed4j.module.domain.javabuildprofile.SeedModuleGradleProfilePlugins.SeedModuleGradleProfilePluginBuilder;
import com.seed4j.module.domain.javabuildprofile.SeedModuleJavaBuildProfiles.SeedModuleJavaBuildProfilesBuilder;
import com.seed4j.module.domain.javadependency.SeedModuleJavaDependencies;
import com.seed4j.module.domain.javadependency.SeedModuleJavaDependencies.SeedModuleJavaDependenciesBuilder;
import com.seed4j.module.domain.mavenplugin.SeedModuleMavenPlugins;
import com.seed4j.module.domain.mavenplugin.SeedModuleMavenPlugins.SeedModuleMavenPluginsBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;

public final class SeedModuleJavaBuildProfile {

  private final BuildProfileId buildProfileId;
  private final Optional<BuildProfileActivation> activation;
  private final SeedModuleBuildProperties properties;
  private final SeedModuleMavenPlugins mavenPlugins;
  private final SeedModuleGradleProfilePlugins gradleProfilePlugins;
  private final SeedModuleJavaDependencies javaDependencies;

  private SeedModuleJavaBuildProfile(SeedModuleJavaBuildProfileBuilder builder) {
    Assert.notNull("buildProfileId", builder.buildProfileId);
    Assert.notNull("propertiesBuilder", builder.propertiesBuilder);
    Assert.notNull("mavenPluginsBuilder", builder.mavenPluginsBuilder);
    Assert.notNull("gradlePluginsBuilder", builder.gradleProfilePluginsBuilder);
    Assert.notNull("javaDependenciesBuilder", builder.javaDependenciesBuilder);
    this.buildProfileId = builder.buildProfileId;
    this.activation = Optional.ofNullable(builder.activation);
    this.properties = builder.propertiesBuilder.build();
    this.mavenPlugins = builder.mavenPluginsBuilder.build();
    this.gradleProfilePlugins = builder.gradleProfilePluginsBuilder.build();
    this.javaDependencies = builder.javaDependenciesBuilder.build();
  }

  public static SeedModuleJavaBuildProfileBuilder builder(SeedModuleJavaBuildProfilesBuilder profiles, BuildProfileId buildProfileId) {
    return new SeedModuleJavaBuildProfileBuilder(profiles, buildProfileId);
  }

  public BuildProfileId id() {
    return buildProfileId;
  }

  public Optional<BuildProfileActivation> activation() {
    return activation;
  }

  public SeedModuleBuildProperties properties() {
    return properties;
  }

  public SeedModuleMavenPlugins mavenPlugins() {
    return mavenPlugins;
  }

  public SeedModuleGradleProfilePlugins gradlePlugins() {
    return gradleProfilePlugins;
  }

  public SeedModuleJavaDependencies javaDependencies() {
    return javaDependencies;
  }

  public static final class SeedModuleJavaBuildProfileBuilder {

    private final SeedModuleJavaBuildProfilesBuilder profiles;
    private final BuildProfileId buildProfileId;
    private BuildProfileActivation activation;
    private final SeedModuleBuildPropertiesBuilder<SeedModuleJavaBuildProfileBuilder> propertiesBuilder = SeedModuleBuildProperties.builder(
      this
    );
    private final SeedModuleMavenPluginsBuilder<SeedModuleJavaBuildProfileBuilder> mavenPluginsBuilder = SeedModuleMavenPlugins.builder(
      this
    );
    private final SeedModuleGradleProfilePluginBuilder gradleProfilePluginsBuilder = SeedModuleGradleProfilePlugins.builder(this);
    private final SeedModuleJavaDependenciesBuilder<SeedModuleJavaBuildProfileBuilder> javaDependenciesBuilder =
      SeedModuleJavaDependencies.builder(this);

    private SeedModuleJavaBuildProfileBuilder(SeedModuleJavaBuildProfilesBuilder profiles, BuildProfileId buildProfileId) {
      Assert.notNull("profiles", profiles);
      Assert.notNull("buildProfileId", buildProfileId);

      this.profiles = profiles;
      this.buildProfileId = buildProfileId;
    }

    public SeedModuleJavaBuildProfilesBuilder and() {
      return profiles;
    }

    public SeedModuleJavaBuildProfile build() {
      return new SeedModuleJavaBuildProfile(this);
    }

    public SeedModuleJavaBuildProfileBuilder activation(BuildProfileActivation activation) {
      Assert.notNull("activation", activation);
      this.activation = activation;

      return this;
    }

    public SeedModuleJavaBuildProfileBuilder activation(BuildProfileActivationBuilder activationBuilder) {
      Assert.notNull("activationBuilder", activationBuilder);

      return activation(activationBuilder.build());
    }

    public SeedModuleBuildPropertiesBuilder<SeedModuleJavaBuildProfileBuilder> properties() {
      return propertiesBuilder;
    }

    public SeedModuleMavenPluginsBuilder<SeedModuleJavaBuildProfileBuilder> mavenPlugins() {
      return mavenPluginsBuilder;
    }

    public SeedModuleGradleProfilePluginBuilder gradleProfilePlugins() {
      return gradleProfilePluginsBuilder;
    }

    public SeedModuleJavaDependenciesBuilder<SeedModuleJavaBuildProfileBuilder> javaDependencies() {
      return javaDependenciesBuilder;
    }
  }
}
