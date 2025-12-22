package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

abstract sealed class JavaDependencyCommandsCreator
  permits DirectJavaDependency, JavaAnnotationProcessingDependency, JavaDependencyManagement {

  private final JavaDependency dependency;

  JavaDependencyCommandsCreator(JavaDependency dependency) {
    Assert.notNull("dependency", dependency);

    this.dependency = dependency;
  }

  JavaBuildCommands changeCommands(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies,
    Optional<BuildProfileId> buildProfile
  ) {
    Assert.notNull("currentVersion", currentVersions);
    Assert.notNull("projectDependencies", projectDependencies);

    Collection<JavaBuildCommand> dependencyCommands = dependencyCommands(projectDependencies, buildProfile);
    Collection<JavaBuildCommand> versionCommands = versionCommands(currentVersions, projectDependencies, buildProfile);

    return new JavaBuildCommands(Stream.of(dependencyCommands, versionCommands).flatMap(Collection::stream).toList());
  }

  protected JavaDependency dependency() {
    return dependency;
  }

  protected abstract Collection<JavaBuildCommand> dependencyCommands(
    ProjectJavaDependencies projectDependencies,
    Optional<BuildProfileId> buildProfile
  );

  protected abstract Collection<JavaBuildCommand> versionCommands(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies,
    Optional<BuildProfileId> buildProfile
  );
}
