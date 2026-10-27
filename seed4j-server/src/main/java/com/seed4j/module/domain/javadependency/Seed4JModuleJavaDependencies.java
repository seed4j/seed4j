package com.seed4j.module.domain.javadependency;

import static com.seed4j.module.domain.javadependency.JavaDependencyScope.TEST;

import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.RemoveDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Seed4JModuleJavaDependencies {

  private final Collection<DependencyId> dependenciesToRemove;
  private final Collection<JavaDependencyManagement> dependenciesManagement;
  private final Collection<DependencyId> dependenciesManagementToRemove;
  private final Collection<DirectJavaDependency> dependencies;

  private Seed4JModuleJavaDependencies(Seed4JModuleJavaDependenciesBuilder<?> builder) {
    dependenciesToRemove = builder.dependenciesToRemove;
    dependenciesManagement = builder.dependenciesManagement;
    dependenciesManagementToRemove = builder.dependenciesManagementToRemove;
    dependencies = builder.dependencies;
  }

  public static <M> Seed4JModuleJavaDependenciesBuilder<M> builder(M module) {
    return new Seed4JModuleJavaDependenciesBuilder<>(module);
  }

  public JavaBuildCommands buildChanges(JavaDependenciesVersions versions, ProjectJavaDependencies projectDependencies) {
    return buildChanges(versions, projectDependencies, Optional.empty());
  }

  public JavaBuildCommands buildChanges(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectJavaDependencies,
    BuildProfileId buildProfile
  ) {
    Assert.notNull("buildProfile", buildProfile);
    return buildChanges(versions, projectJavaDependencies, Optional.of(buildProfile));
  }

  private JavaBuildCommands buildChanges(
    JavaDependenciesVersions versions,
    ProjectJavaDependencies projectDependencies,
    Optional<BuildProfileId> buildProfile
  ) {
    Assert.notNull("versions", versions);
    Assert.notNull("projectDependencies", projectDependencies);

    return Stream.of(
      dependenciesToRemoveCommands(buildProfile),
      dependenciesManagementChanges(versions, projectDependencies, buildProfile),
      dependenciesManagementToRemoveCommands(buildProfile),
      dependenciesChanges(versions, projectDependencies, buildProfile)
    )
      .flatMap(Function.identity())
      .reduce(JavaBuildCommands.EMPTY, JavaBuildCommands::merge);
  }

  private Stream<JavaBuildCommands> dependenciesToRemoveCommands(Optional<BuildProfileId> buildProfile) {
    return Stream.of(new JavaBuildCommands(dependenciesToRemove.stream().map(toDependencyToRemove(buildProfile)).toList()));
  }

  private Function<DependencyId, JavaBuildCommand> toDependencyToRemove(Optional<BuildProfileId> buildProfile) {
    return dependency -> new RemoveDirectJavaDependency(dependency, buildProfile);
  }

  private Stream<JavaBuildCommands> dependenciesManagementChanges(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies,
    Optional<BuildProfileId> buildProfile
  ) {
    return dependenciesManagement.stream().map(dependency -> dependency.changeCommands(currentVersions, projectDependencies, buildProfile));
  }

  private Stream<JavaBuildCommands> dependenciesManagementToRemoveCommands(Optional<BuildProfileId> buildProfile) {
    return Stream.of(
      new JavaBuildCommands(dependenciesManagementToRemove.stream().map(toDependencyManagementToRemove(buildProfile)).toList())
    );
  }

  private Function<DependencyId, JavaBuildCommand> toDependencyManagementToRemove(Optional<BuildProfileId> buildProfile) {
    return dependency -> new RemoveJavaDependencyManagement(dependency, buildProfile);
  }

  private Stream<JavaBuildCommands> dependenciesChanges(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies,
    Optional<BuildProfileId> buildProfile
  ) {
    return dependencies.stream().map(dependency -> dependency.changeCommands(currentVersions, projectDependencies, buildProfile));
  }

  public static final class Seed4JModuleJavaDependenciesBuilder<T> {

    private static final String DEPENDENCY = "dependency";

    private final T parentModuleBuilder;
    private final Collection<DependencyId> dependenciesToRemove = new ArrayList<>();
    private final Collection<DirectJavaDependency> dependencies = new ArrayList<>();
    private final Collection<JavaDependencyManagement> dependenciesManagement = new ArrayList<>();
    private final Collection<DependencyId> dependenciesManagementToRemove = new ArrayList<>();

    private Seed4JModuleJavaDependenciesBuilder(T parentModuleBuilder) {
      Assert.notNull("module", parentModuleBuilder);

      this.parentModuleBuilder = parentModuleBuilder;
    }

    public Seed4JModuleJavaDependenciesBuilder<T> removeDependency(DependencyId dependency) {
      Assert.notNull(DEPENDENCY, dependency);

      dependenciesToRemove.add(dependency);

      return this;
    }

    public Seed4JModuleJavaDependenciesBuilder<T> addDependency(GroupId groupId, ArtifactId artifactId) {
      JavaDependency dependency = JavaDependency.builder().groupId(groupId).artifactId(artifactId).build();

      return addDependency(dependency);
    }

    public Seed4JModuleJavaDependenciesBuilder<T> addDependency(GroupId groupId, ArtifactId artifactId, VersionSlug versionSlug) {
      JavaDependency dependency = JavaDependency.builder().groupId(groupId).artifactId(artifactId).versionSlug(versionSlug).build();

      return addDependency(dependency);
    }

    public Seed4JModuleJavaDependenciesBuilder<T> addTestDependency(GroupId groupId, ArtifactId artifactId) {
      JavaDependency dependency = JavaDependency.builder().groupId(groupId).artifactId(artifactId).scope(TEST).build();

      return addDependency(dependency);
    }

    public Seed4JModuleJavaDependenciesBuilder<T> addTestDependency(GroupId groupId, ArtifactId artifactId, VersionSlug versionSlug) {
      JavaDependency dependency = JavaDependency.builder()
        .groupId(groupId)
        .artifactId(artifactId)
        .versionSlug(versionSlug)
        .scope(TEST)
        .build();

      return addDependency(dependency);
    }

    public Seed4JModuleJavaDependenciesBuilder<T> addDependency(JavaDependency dependency) {
      Assert.notNull(DEPENDENCY, dependency);

      dependencies.add(new DirectJavaDependency(dependency));

      return this;
    }

    public Seed4JModuleJavaDependenciesBuilder<T> addDependencyManagement(JavaDependency dependency) {
      Assert.notNull(DEPENDENCY, dependency);

      dependenciesManagement.add(new JavaDependencyManagement(dependency));

      return this;
    }

    public Seed4JModuleJavaDependenciesBuilder<T> removeDependencyManagement(DependencyId dependency) {
      Assert.notNull(DEPENDENCY, dependency);

      dependenciesManagementToRemove.add(dependency);

      return this;
    }

    public T and() {
      return parentModuleBuilder;
    }

    public Seed4JModuleJavaDependencies build() {
      return new Seed4JModuleJavaDependencies(this);
    }
  }
}
