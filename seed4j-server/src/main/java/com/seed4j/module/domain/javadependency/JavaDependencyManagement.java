package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import java.util.Collection;
import java.util.Optional;

public final class JavaDependencyManagement extends JavaDependencyCommandsCreator {

  JavaDependencyManagement(JavaDependency dependency) {
    super(dependency);
  }

  @Override
  protected Collection<JavaBuildCommand> dependencyCommands(
    ProjectJavaDependencies projectDependencies,
    Optional<BuildProfileId> buildProfile
  ) {
    return dependency().dependencyCommands(
      DependenciesCommandsFactory.MANAGEMENT,
      projectDependencies.dependencyManagement(dependency().id()),
      buildProfile
    );
  }

  @Override
  protected Collection<JavaBuildCommand> versionCommands(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies,
    Optional<BuildProfileId> buildProfile
  ) {
    return dependency().versionCommands(currentVersions, projectDependencies, dependencyCommands(projectDependencies, buildProfile));
  }
}
