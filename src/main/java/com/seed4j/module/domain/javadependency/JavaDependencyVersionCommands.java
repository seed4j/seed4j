package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

final class JavaDependencyVersionCommands {

  private JavaDependencyVersionCommands() {}

  static Collection<JavaBuildCommand> build(
    Optional<VersionSlug> version,
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies,
    Collection<JavaBuildCommand> dependencyCommands,
    Predicate<JavaBuildCommand> isAddCommand
  ) {
    return version
      .flatMap(toVersion(currentVersions, projectDependencies, dependencyCommands, isAddCommand))
      .map(v -> List.<JavaBuildCommand>of(new SetVersion(v)))
      .orElse(List.of());
  }

  static Function<VersionSlug, Optional<JavaDependencyVersion>> toVersion(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    return toVersion(currentVersions, projectDependencies, List.of(), _ -> false);
  }

  private static Function<VersionSlug, Optional<JavaDependencyVersion>> toVersion(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies,
    Collection<JavaBuildCommand> dependencyCommands,
    Predicate<JavaBuildCommand> isAddCommand
  ) {
    return slug -> {
      JavaDependencyVersion currentVersion = currentVersions.get(slug);

      return projectDependencies
        .version(slug)
        .map(toVersionToUse(currentVersion, dependencyCommands, isAddCommand))
        .orElseGet(() -> Optional.of(currentVersion));
    };
  }

  private static Function<JavaDependencyVersion, Optional<JavaDependencyVersion>> toVersionToUse(
    JavaDependencyVersion currentVersion,
    Collection<JavaBuildCommand> dependencyCommands,
    Predicate<JavaBuildCommand> isAddCommand
  ) {
    return version -> {
      if (version.equals(currentVersion) && dependencyCommands.stream().noneMatch(isAddCommand)) {
        return Optional.empty();
      }

      return Optional.of(currentVersion);
    };
  }
}
