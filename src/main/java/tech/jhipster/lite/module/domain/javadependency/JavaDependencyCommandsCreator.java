package tech.jhipster.lite.module.domain.javadependency;

import java.util.Collection;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.shared.error.domain.Assert;

abstract class JavaDependencyCommandsCreator {

  private final JavaDependency dependency;

  JavaDependencyCommandsCreator(JavaDependency dependency) {
    Assert.notNull("dependency", dependency);

    this.dependency = dependency;
  }

  JavaBuildCommands changeCommands(JavaDependenciesVersions currentVersions, ProjectJavaDependencies projectDependencies) {
    Assert.notNull("currentVersion", currentVersions);
    Assert.notNull("projectDependencies", projectDependencies);

    Collection<JavaBuildCommand> versionCommands = dependency.versionCommands(currentVersions, projectDependencies);
    Collection<JavaBuildCommand> dependencyCommands = dependencyCommands(projectDependencies);

    return new JavaBuildCommands(Stream.of(versionCommands, dependencyCommands).flatMap(Collection::stream).toList());
  }

  protected JavaDependency dependency() {
    return dependency;
  }

  protected abstract Collection<JavaBuildCommand> dependencyCommands(ProjectJavaDependencies projectDependencies);
}
