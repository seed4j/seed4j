package com.seed4j.module.domain.javabuildprofile;

import com.seed4j.module.domain.buildproperties.Seed4JModuleBuildProperties;
import com.seed4j.module.domain.buildproperties.Seed4JModuleBuildProperties.Seed4JModuleBuildPropertiesBuilder;
import com.seed4j.module.domain.javabuildprofile.BuildProfileActivation.BuildProfileActivationBuilder;
import com.seed4j.module.domain.javabuildprofile.Seed4JModuleGradleProfilePlugins.Seed4JModuleGradleProfilePluginBuilder;
import com.seed4j.module.domain.javabuildprofile.Seed4JModuleJavaBuildProfiles.Seed4JModuleJavaBuildProfilesBuilder;
import com.seed4j.module.domain.javadependency.Seed4JModuleJavaDependencies;
import com.seed4j.module.domain.javadependency.Seed4JModuleJavaDependencies.Seed4JModuleJavaDependenciesBuilder;
import com.seed4j.module.domain.mavenplugin.Seed4JModuleMavenPlugins;
import com.seed4j.module.domain.mavenplugin.Seed4JModuleMavenPlugins.Seed4JModuleMavenPluginsBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;

public final class Seed4JModuleJavaBuildProfile {

  private final BuildProfileId buildProfileId;
  private final Optional<BuildProfileActivation> activation;
  private final Seed4JModuleBuildProperties properties;
  private final Seed4JModuleMavenPlugins mavenPlugins;
  private final Seed4JModuleGradleProfilePlugins gradleProfilePlugins;
  private final Seed4JModuleJavaDependencies javaDependencies;

  private Seed4JModuleJavaBuildProfile(Seed4JModuleJavaBuildProfileBuilder builder) {
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

  public static Seed4JModuleJavaBuildProfileBuilder builder(Seed4JModuleJavaBuildProfilesBuilder profiles, BuildProfileId buildProfileId) {
    return new Seed4JModuleJavaBuildProfileBuilder(profiles, buildProfileId);
  }

  public BuildProfileId id() {
    return buildProfileId;
  }

  public Optional<BuildProfileActivation> activation() {
    return activation;
  }

  public Seed4JModuleBuildProperties properties() {
    return properties;
  }

  public Seed4JModuleMavenPlugins mavenPlugins() {
    return mavenPlugins;
  }

  public Seed4JModuleGradleProfilePlugins gradlePlugins() {
    return gradleProfilePlugins;
  }

  public Seed4JModuleJavaDependencies javaDependencies() {
    return javaDependencies;
  }

  public static final class Seed4JModuleJavaBuildProfileBuilder {

    private final Seed4JModuleJavaBuildProfilesBuilder profiles;
    private final BuildProfileId buildProfileId;
    private BuildProfileActivation activation;
    private final Seed4JModuleBuildPropertiesBuilder<Seed4JModuleJavaBuildProfileBuilder> propertiesBuilder =
      Seed4JModuleBuildProperties.builder(this);
    private final Seed4JModuleMavenPluginsBuilder<Seed4JModuleJavaBuildProfileBuilder> mavenPluginsBuilder =
      Seed4JModuleMavenPlugins.builder(this);
    private final Seed4JModuleGradleProfilePluginBuilder gradleProfilePluginsBuilder = Seed4JModuleGradleProfilePlugins.builder(this);
    private final Seed4JModuleJavaDependenciesBuilder<Seed4JModuleJavaBuildProfileBuilder> javaDependenciesBuilder =
      Seed4JModuleJavaDependencies.builder(this);

    private Seed4JModuleJavaBuildProfileBuilder(Seed4JModuleJavaBuildProfilesBuilder profiles, BuildProfileId buildProfileId) {
      Assert.notNull("profiles", profiles);
      Assert.notNull("buildProfileId", buildProfileId);

      this.profiles = profiles;
      this.buildProfileId = buildProfileId;
    }

    public Seed4JModuleJavaBuildProfilesBuilder and() {
      return profiles;
    }

    public Seed4JModuleJavaBuildProfile build() {
      return new Seed4JModuleJavaBuildProfile(this);
    }

    public Seed4JModuleJavaBuildProfileBuilder activation(BuildProfileActivation activation) {
      Assert.notNull("activation", activation);
      this.activation = activation;

      return this;
    }

    public Seed4JModuleJavaBuildProfileBuilder activation(BuildProfileActivationBuilder activationBuilder) {
      Assert.notNull("activationBuilder", activationBuilder);

      return activation(activationBuilder.build());
    }

    public Seed4JModuleBuildPropertiesBuilder<Seed4JModuleJavaBuildProfileBuilder> properties() {
      return propertiesBuilder;
    }

    public Seed4JModuleMavenPluginsBuilder<Seed4JModuleJavaBuildProfileBuilder> mavenPlugins() {
      return mavenPluginsBuilder;
    }

    public Seed4JModuleGradleProfilePluginBuilder gradleProfilePlugins() {
      return gradleProfilePluginsBuilder;
    }

    public Seed4JModuleJavaDependenciesBuilder<Seed4JModuleJavaBuildProfileBuilder> javaDependencies() {
      return javaDependenciesBuilder;
    }
  }
}
