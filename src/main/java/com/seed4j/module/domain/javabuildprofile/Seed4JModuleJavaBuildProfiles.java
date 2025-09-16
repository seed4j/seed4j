package com.seed4j.module.domain.javabuildprofile;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.javabuild.command.AddJavaBuildProfile;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuildprofile.Seed4JModuleJavaBuildProfile.Seed4JModuleJavaBuildProfileBuilder;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Seed4JModuleJavaBuildProfiles {

  private final Collection<Seed4JModuleJavaBuildProfile> profiles;

  private Seed4JModuleJavaBuildProfiles(Seed4JModuleJavaBuildProfilesBuilder builder) {
    this.profiles = builder.profiles.values().stream().map(Seed4JModuleJavaBuildProfileBuilder::build).toList();
  }

  public static Seed4JModuleJavaBuildProfilesBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleJavaBuildProfilesBuilder(module);
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

  private Function<Seed4JModuleJavaBuildProfile, JavaBuildCommand> toAddProfileCommands() {
    return profile -> new AddJavaBuildProfile(profile.id(), profile.activation());
  }

  private Function<Seed4JModuleJavaBuildProfile, Stream<JavaBuildCommand>> toAddPropertyCommands() {
    return profile -> profile.properties().buildChanges(profile.id());
  }

  private Function<Seed4JModuleJavaBuildProfile, Stream<JavaBuildCommand>> toMavenPluginCommands(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectJavaDependencies
  ) {
    return profile -> profile.mavenPlugins().buildChanges(versions, projectJavaDependencies, profile.id()).get().stream();
  }

  private Function<Seed4JModuleJavaBuildProfile, Stream<JavaBuildCommand>> toGradlePluginCommands(JavaDependenciesVersions versions) {
    return profile -> profile.gradlePlugins().buildChanges(versions, profile.id()).get().stream();
  }

  private Function<Seed4JModuleJavaBuildProfile, Stream<JavaBuildCommand>> toJavaDependenciesCommands(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectJavaDependencies
  ) {
    return profile -> profile.javaDependencies().buildChanges(versions, projectJavaDependencies, profile.id()).get().stream();
  }

  public static final class Seed4JModuleJavaBuildProfilesBuilder {

    private final Seed4JModuleBuilder module;
    private final Map<BuildProfileId, Seed4JModuleJavaBuildProfileBuilder> profiles = new HashMap<>();

    private Seed4JModuleJavaBuildProfilesBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleJavaBuildProfileBuilder addProfile(BuildProfileId buildProfileId) {
      Assert.notNull("buildProfileId", buildProfileId);

      return profiles.computeIfAbsent(buildProfileId, id -> Seed4JModuleJavaBuildProfile.builder(this, buildProfileId));
    }

    public Seed4JModuleJavaBuildProfileBuilder addProfile(String buildProfileId) {
      return addProfile(new BuildProfileId(buildProfileId));
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModuleJavaBuildProfiles build() {
      return new Seed4JModuleJavaBuildProfiles(this);
    }
  }
}
