package com.seed4j.module.domain.javabuildprofile;

import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import com.seed4j.module.domain.javabuild.command.AddJavaBuildProfile;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuildprofile.JHipsterModuleJavaBuildProfile.JHipsterModuleJavaBuildProfileBuilder;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public final class JHipsterModuleJavaBuildProfiles {

  private final Collection<JHipsterModuleJavaBuildProfile> profiles;

  private JHipsterModuleJavaBuildProfiles(JHipsterModuleJavaBuildProfilesBuilder builder) {
    this.profiles = builder.profiles.values().stream().map(JHipsterModuleJavaBuildProfileBuilder::build).toList();
  }

  public static JHipsterModuleJavaBuildProfilesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleJavaBuildProfilesBuilder(module);
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

  private Function<JHipsterModuleJavaBuildProfile, JavaBuildCommand> toAddProfileCommands() {
    return profile -> new AddJavaBuildProfile(profile.id(), profile.activation());
  }

  private Function<JHipsterModuleJavaBuildProfile, Stream<JavaBuildCommand>> toAddPropertyCommands() {
    return profile -> profile.properties().buildChanges(profile.id());
  }

  private Function<JHipsterModuleJavaBuildProfile, Stream<JavaBuildCommand>> toMavenPluginCommands(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectJavaDependencies
  ) {
    return profile -> profile.mavenPlugins().buildChanges(versions, projectJavaDependencies, profile.id()).get().stream();
  }

  private Function<JHipsterModuleJavaBuildProfile, Stream<JavaBuildCommand>> toGradlePluginCommands(JavaDependenciesVersions versions) {
    return profile -> profile.gradlePlugins().buildChanges(versions, profile.id()).get().stream();
  }

  private Function<JHipsterModuleJavaBuildProfile, Stream<JavaBuildCommand>> toJavaDependenciesCommands(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectJavaDependencies
  ) {
    return profile -> profile.javaDependencies().buildChanges(versions, projectJavaDependencies, profile.id()).get().stream();
  }

  public static final class JHipsterModuleJavaBuildProfilesBuilder {

    private final JHipsterModuleBuilder module;
    private final Map<BuildProfileId, JHipsterModuleJavaBuildProfileBuilder> profiles = new HashMap<>();

    private JHipsterModuleJavaBuildProfilesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleJavaBuildProfileBuilder addProfile(BuildProfileId buildProfileId) {
      Assert.notNull("buildProfileId", buildProfileId);

      return profiles.computeIfAbsent(buildProfileId, id -> JHipsterModuleJavaBuildProfile.builder(this, buildProfileId));
    }

    public JHipsterModuleJavaBuildProfileBuilder addProfile(String buildProfileId) {
      return addProfile(new BuildProfileId(buildProfileId));
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleJavaBuildProfiles build() {
      return new JHipsterModuleJavaBuildProfiles(this);
    }
  }
}
