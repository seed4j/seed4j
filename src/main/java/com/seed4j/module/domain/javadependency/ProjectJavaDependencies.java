package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class ProjectJavaDependencies {

  public static final ProjectJavaDependencies EMPTY = builder()
    .versions(ProjectJavaDependenciesVersions.EMPTY)
    .dependenciesManagement(JavaDependencies.EMPTY)
    .dependencies(JavaDependencies.EMPTY)
    .annotationProcessingDependencies(JavaAnnotationProcessorDependencies.EMPTY);

  private final ProjectJavaDependenciesVersions versions;
  private final JavaDependencies dependenciesManagement;
  private final JavaDependencies dependencies;
  private final JavaAnnotationProcessorDependencies annotationProcessingDependencies;

  private ProjectJavaDependencies(ProjectJavaDependenciesBuilder builder) {
    Assert.notNull("versions", builder.versions);
    Assert.notNull("dependenciesManagement", builder.dependenciesManagement);
    Assert.notNull("dependencies", builder.dependencies);
    Assert.notNull("annotationProcessingDependencies", builder.annotationProcessingDependencies);

    versions = builder.versions;
    dependenciesManagement = builder.dependenciesManagement;
    dependencies = builder.dependencies;
    annotationProcessingDependencies = builder.annotationProcessingDependencies;
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

  public Optional<JavaAnnotationProcessorDependency> annotationProcessor(DependencyId id) {
    return annotationProcessingDependencies.get(id);
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
      .dependenciesManagement(dependenciesManagement.merge(other.dependenciesManagement))
      .dependencies(dependencies.merge(other.dependencies))
      .annotationProcessingDependencies(annotationProcessingDependencies.merge(other.annotationProcessingDependencies));
  }

  public static ProjectJavaDependenciesVersionsBuilder builder() {
    return new ProjectJavaDependenciesBuilder();
  }

  public sealed interface ProjectJavaDependenciesVersionsBuilder permits ProjectJavaDependenciesBuilder {
    ProjectJavaDependenciesDependenciesManagementBuilder versions(ProjectJavaDependenciesVersions versions);
  }

  public sealed interface ProjectJavaDependenciesDependenciesManagementBuilder permits ProjectJavaDependenciesBuilder {
    ProjectJavaDependenciesDependenciesBuilder dependenciesManagement(JavaDependencies dependenciesManagement);
  }

  public sealed interface ProjectJavaDependenciesDependenciesBuilder permits ProjectJavaDependenciesBuilder {
    ProjectJavaDependenciesAnnotationProcessingDependenciesBuilder dependencies(JavaDependencies dependencies);
  }

  public sealed interface ProjectJavaDependenciesAnnotationProcessingDependenciesBuilder permits ProjectJavaDependenciesBuilder {
    ProjectJavaDependencies annotationProcessingDependencies(JavaAnnotationProcessorDependencies annotationProcessingDependencies);
  }

  private static final class ProjectJavaDependenciesBuilder
    implements
      ProjectJavaDependenciesVersionsBuilder,
      ProjectJavaDependenciesDependenciesManagementBuilder,
      ProjectJavaDependenciesDependenciesBuilder,
      ProjectJavaDependenciesAnnotationProcessingDependenciesBuilder
  {

    private @Nullable ProjectJavaDependenciesVersions versions;
    private @Nullable JavaDependencies dependenciesManagement;
    private @Nullable JavaDependencies dependencies;
    private @Nullable JavaAnnotationProcessorDependencies annotationProcessingDependencies;

    @Override
    public ProjectJavaDependenciesDependenciesManagementBuilder versions(ProjectJavaDependenciesVersions versions) {
      this.versions = versions;

      return this;
    }

    @Override
    public ProjectJavaDependenciesDependenciesBuilder dependenciesManagement(JavaDependencies dependenciesManagement) {
      this.dependenciesManagement = dependenciesManagement;

      return this;
    }

    @Override
    public ProjectJavaDependenciesAnnotationProcessingDependenciesBuilder dependencies(JavaDependencies dependencies) {
      this.dependencies = dependencies;

      return this;
    }

    @Override
    public ProjectJavaDependencies annotationProcessingDependencies(JavaAnnotationProcessorDependencies annotationProcessingDependencies) {
      this.annotationProcessingDependencies = annotationProcessingDependencies;

      return new ProjectJavaDependencies(this);
    }
  }
}
