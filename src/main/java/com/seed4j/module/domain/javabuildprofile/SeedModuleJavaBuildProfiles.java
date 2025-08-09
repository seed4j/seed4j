package com.seed4j.module.domain.javabuildprofile;

import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import com.seed4j.module.domain.javabuild.command.AddJavaBuildProfile;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuildprofile.SeedModuleJavaBuildProfile.SeedModuleJavaBuildProfileBuilder;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public final class SeedModuleJavaBuildProfiles {

  private final Collection<SeedModuleJavaBuildProfile> profiles;

  private SeedModuleJavaBuildProfiles(SeedModuleJavaBuildProfilesBuilder builder) {
    this.profiles = builder.profiles.values().stream().map(SeedModuleJavaBuildProfileBuilder::build).toList();
  }

  public static SeedModuleJavaBuildProfilesBuilder builder(JHipsterModuleBuilder module) {
    return new SeedModuleJavaBuildProfilesBuilder(module);
  }

  public JavaBuildCommands buildChanges(JavaDependenciesVersions versions, ProjectJavaDependencies projectJavaDependencies) {
    Stream<JavaBuildCommand> addProfileCommands = profiles.stream().map(toAddProfileCommands());
    Stream<JavaBuildCommand> addPropertyCommands = profiles.stream().flatMap(toAddPropertyCommands());
    Stream<JavaBuildCommand> mavenPluginCommands = profiles.stream().flatMap(toMavenPluginCommands(versions, projectJavaDependencies));
    Stream<JavaBuildCommand> gradlePluginCommands = profiles.stream().flatMap(toGradlePluginCommands(versions));
    Stream<JavaBuildCommand> javaDependenciesCommands = profiles
      .stream()
      .flatMap(toJavaDependenciesCommands(versions, projectJavaDependencies));

    Collection<JavaBuildCommand> commands = Stream.of(
      addProfileCommands,
      addPropertyCommands,
      mavenPluginCommands,
      gradlePluginCommands,
      javaDependenciesCommands
    )
      .flatMap(Function.identity())
      .toList();

    return new JavaBuildCommands(commands);
  }

  private Function<SeedModuleJavaBuildProfile, JavaBuildCommand> toAddProfileCommands() {
    return profile -> new AddJavaBuildProfile(profile.id(), profile.activation());
  }

  private Function<SeedModuleJavaBuildProfile, Stream<JavaBuildCommand>> toAddPropertyCommands() {
    return profile -> profile.properties().buildChanges(profile.id());
  }

  private Function<SeedModuleJavaBuildProfile, Stream<JavaBuildCommand>> toMavenPluginCommands(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectJavaDependencies
  ) {
    return profile -> profile.mavenPlugins().buildChanges(versions, projectJavaDependencies, profile.id()).get().stream();
  }

  private Function<SeedModuleJavaBuildProfile, Stream<JavaBuildCommand>> toGradlePluginCommands(JavaDependenciesVersions versions) {
    return profile -> profile.gradlePlugins().buildChanges(versions, profile.id()).get().stream();
  }

  private Function<SeedModuleJavaBuildProfile, Stream<JavaBuildCommand>> toJavaDependenciesCommands(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectJavaDependencies
  ) {
    return profile -> profile.javaDependencies().buildChanges(versions, projectJavaDependencies, profile.id()).get().stream();
  }

  public static final class SeedModuleJavaBuildProfilesBuilder {

    private final JHipsterModuleBuilder module;
    private final Map<BuildProfileId, SeedModuleJavaBuildProfileBuilder> profiles = new HashMap<>();

    private SeedModuleJavaBuildProfilesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public SeedModuleJavaBuildProfileBuilder addProfile(BuildProfileId buildProfileId) {
      Assert.notNull("buildProfileId", buildProfileId);

      return profiles.computeIfAbsent(buildProfileId, id -> SeedModuleJavaBuildProfile.builder(this, buildProfileId));
    }

    public SeedModuleJavaBuildProfileBuilder addProfile(String buildProfileId) {
      return addProfile(new BuildProfileId(buildProfileId));
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public SeedModuleJavaBuildProfiles build() {
      return new SeedModuleJavaBuildProfiles(this);
    }
  }
}
