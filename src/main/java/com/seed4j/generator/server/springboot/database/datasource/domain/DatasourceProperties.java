package com.seed4j.generator.server.springboot.database.datasource.domain;

import static com.seed4j.module.domain.SeedModule.javaDependency;

import com.seed4j.module.domain.docker.DockerImageName;
import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;

public final class DatasourceProperties {

  private final String id;
  private final String databaseName;
  private final JavaDependency driverDependency;
  private final String driverClassName;
  private final DockerImageName dockerImageName;
  private final ArtifactId testContainerArtifactId;

  private DatasourceProperties(DatasourcePropertiesBuilder builder) {
    this.id = builder.id;
    this.databaseName = builder.databaseName;
    this.driverDependency = builder.driverDependency;
    this.driverClassName = builder.driverClassName;
    this.dockerImageName = builder.dockerImageName;
    this.testContainerArtifactId = builder.testContainerArtifactId;
  }

  public static DatasourcePropertiesIdBuilder builder() {
    return new DatasourcePropertiesBuilder();
  }

  public String id() {
    return id;
  }

  public String databaseName() {
    return databaseName;
  }

  public JavaDependency driverDependency() {
    return driverDependency;
  }

  public String driverClassName() {
    return driverClassName;
  }

  public DockerImageName dockerImageName() {
    return dockerImageName;
  }

  public JavaDependency testContainerDependency() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId(testContainerArtifactId)
      .dependencySlug("%s-%s".formatted("testcontainers", testContainerArtifactId))
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  public sealed interface DatasourcePropertiesIdBuilder permits DatasourcePropertiesBuilder {
    DatasourcePropertiesDatabaseNameBuilder id(String id);
  }

  public sealed interface DatasourcePropertiesDatabaseNameBuilder permits DatasourcePropertiesBuilder {
    DatasourcePropertiesDriverDependencyBuilder databaseName(String databaseName);
  }

  public sealed interface DatasourcePropertiesDriverDependencyBuilder permits DatasourcePropertiesBuilder {
    DatasourcePropertiesDriverClassNameBuilder driverDependency(JavaDependency driverDependency);
  }

  public sealed interface DatasourcePropertiesDriverClassNameBuilder permits DatasourcePropertiesBuilder {
    DatasourcePropertiesDockerImageNameBuilder driverClassName(String driverClassName);
  }

  public sealed interface DatasourcePropertiesDockerImageNameBuilder permits DatasourcePropertiesBuilder {
    DatasourcePropertiesTestContainerArtifactIdBuilder dockerImageName(DockerImageName dockerImageName);
  }

  public sealed interface DatasourcePropertiesTestContainerArtifactIdBuilder permits DatasourcePropertiesBuilder {
    DatasourceProperties testContainerArtifactId(ArtifactId testContainerArtifactId);
  }

  private static final class DatasourcePropertiesBuilder
    implements
      DatasourcePropertiesIdBuilder,
      DatasourcePropertiesDatabaseNameBuilder,
      DatasourcePropertiesDriverDependencyBuilder,
      DatasourcePropertiesDriverClassNameBuilder,
      DatasourcePropertiesDockerImageNameBuilder,
      DatasourcePropertiesTestContainerArtifactIdBuilder {

    private String id;
    private String databaseName;
    private JavaDependency driverDependency;
    private String driverClassName;
    private DockerImageName dockerImageName;
    private ArtifactId testContainerArtifactId;

    @Override
    public DatasourcePropertiesDatabaseNameBuilder id(String id) {
      this.id = id;

      return this;
    }

    @Override
    public DatasourcePropertiesDriverDependencyBuilder databaseName(String databaseName) {
      this.databaseName = databaseName;

      return this;
    }

    @Override
    public DatasourcePropertiesDriverClassNameBuilder driverDependency(JavaDependency driverDependency) {
      this.driverDependency = driverDependency;

      return this;
    }

    @Override
    public DatasourcePropertiesDockerImageNameBuilder driverClassName(String driverClassName) {
      this.driverClassName = driverClassName;

      return this;
    }

    @Override
    public DatasourcePropertiesTestContainerArtifactIdBuilder dockerImageName(DockerImageName dockerImageName) {
      this.dockerImageName = dockerImageName;

      return this;
    }

    @Override
    public DatasourceProperties testContainerArtifactId(ArtifactId testContainerArtifactId) {
      this.testContainerArtifactId = testContainerArtifactId;

      return new DatasourceProperties(this);
    }
  }
}
