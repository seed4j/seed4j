package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;

public final class ProjectJavaDependencies {

  public static final ProjectJavaDependencies EMPTY = builder().versions(null).dependenciesManagements(null).dependencies(null);

  private final ProjectJavaDependenciesVersions versions;
  private final JavaDependencies dependenciesManagement;
  private final JavaDependencies dependencies;

  private ProjectJavaDependencies(ProjectJavaDependenciesBuilder builder) {
    versions = buildVersions(builder.versions);
    dependenciesManagement = buildDependencies(builder.dependenciesManagement);
    dependencies = buildDependencies(builder.dependencies);
  }

  private ProjectJavaDependenciesVersions buildVersions(ProjectJavaDependenciesVersions versions) {
    if (versions == null) {
      return ProjectJavaDependenciesVersions.EMPTY;
    }

    return versions;
  }

  private JavaDependencies buildDependencies(JavaDependencies dependencies) {
    if (dependencies == null) {
      return JavaDependencies.EMPTY;
    }

    return dependencies;
  }

  public static ProjectJavaDependenciesVersionsBuilder builder() {
    return new ProjectJavaDependenciesBuilder();
  }

  public Optional<JavaDependencyVersion> version(VersionSlug slug) {
    return versions.get(slug);
  }

  public Optional<JavaDependency> dependency(DependencyId id) {
    return dependencies.get(id);
  }

  public Optional<JavaDependency> dependencyManagement(DependencyId id) {
    return dependenciesManagement.get(id);
  }

  public ProjectJavaDependenciesVersions versions() {
    return versions;
  }

  public JavaDependencies dependenciesManagement() {
    return dependenciesManagement;
  }

  public JavaDependencies dependencies() {
    return dependencies;
  }

  public ProjectJavaDependencies merge(ProjectJavaDependencies other) {
    Assert.notNull("other", other);

    return builder()
      .versions(versions.merge(other.versions()))
      .dependenciesManagements(dependenciesManagement.merge(other.dependenciesManagement))
      .dependencies(dependencies.merge(other.dependencies));
  }

  private static final class ProjectJavaDependenciesBuilder
    implements
      ProjectJavaDependenciesVersionsBuilder,
      ProjectJavaDependenciesDependenciesManagementBuilder,
      ProjectJavaDependenciesDependenciesBuilder {

    private ProjectJavaDependenciesVersions versions;
    private JavaDependencies dependenciesManagement;
    private JavaDependencies dependencies;

    @Override
    public ProjectJavaDependenciesDependenciesManagementBuilder versions(ProjectJavaDependenciesVersions versions) {
      this.versions = versions;

      return this;
    }

    @Override
    public ProjectJavaDependenciesDependenciesBuilder dependenciesManagements(JavaDependencies dependenciesManagement) {
      this.dependenciesManagement = dependenciesManagement;

      return this;
    }

    @Override
    public ProjectJavaDependencies dependencies(JavaDependencies dependencies) {
      this.dependencies = dependencies;

      return new ProjectJavaDependencies(this);
    }
  }

  public interface ProjectJavaDependenciesVersionsBuilder {
    ProjectJavaDependenciesDependenciesManagementBuilder versions(ProjectJavaDependenciesVersions versions);
  }

  public interface ProjectJavaDependenciesDependenciesManagementBuilder {
    ProjectJavaDependenciesDependenciesBuilder dependenciesManagements(JavaDependencies dependenciesManagement);
  }

  public interface ProjectJavaDependenciesDependenciesBuilder {
    ProjectJavaDependencies dependencies(JavaDependencies dependencies);
  }
}
