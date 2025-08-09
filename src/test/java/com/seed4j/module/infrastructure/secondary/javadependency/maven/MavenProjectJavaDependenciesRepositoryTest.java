package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import static com.seed4j.module.domain.SeedModulesFixture.jsonWebTokenDependencyId;
import static com.seed4j.module.domain.SeedModulesFixture.springBootDependencyId;
import static com.seed4j.module.domain.SeedModulesFixture.springBootDependencyManagement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.module.domain.javadependency.JavaDependencies;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyClassifier;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import com.seed4j.module.domain.javadependency.ProjectJavaDependenciesVersions;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.shared.error.domain.GeneratorException;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenProjectJavaDependenciesRepositoryTest {

  private static final MavenProjectJavaDependenciesRepository projectDependencies = new MavenProjectJavaDependenciesRepository();

  @Test
  void shouldNotReadFromUnreadableMavenFile() {
    assertThatThrownBy(() ->
      projectDependencies.get(new SeedProjectFolder("src/test/resources/projects/maven-unreadable"))
    ).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldGetEmptyDependenciesFromEmptyProject() {
    assertThat(projectDependencies.get(new SeedProjectFolder("src/test/resources/projects/empty"))).isEqualTo(
      ProjectJavaDependencies.EMPTY
    );
  }

  @Test
  void shouldGetEmptyProjectDependenciesFromEmptyMavenFile() {
    assertThat(projectDependencies.get(new SeedProjectFolder("src/test/resources/projects/empty-maven")))
      .usingRecursiveComparison()
      .isEqualTo(ProjectJavaDependencies.EMPTY);
  }

  @Test
  void shouldGetVersionsFromMavenFile() {
    ProjectJavaDependenciesVersions versions = mavenDependencies().versions();

    assertThat(versions.get(new VersionSlug("json-web-token"))).contains(new JavaDependencyVersion("json-web-token", "0.11.5"));
    assertThat(versions.get(new VersionSlug("logstash-logback-encoder.version"))).contains(
      new JavaDependencyVersion("logstash-logback-encoder", "7.2")
    );
    assertThat(versions.get(new VersionSlug("dummy"))).isEmpty();
  }

  @Test
  void shouldGetDependenciesManagementFromMavenFile() {
    JavaDependencies dependencies = mavenDependencies().dependenciesManagement();

    assertThat(dependencies.get(springBootDependencyId()).orElseThrow())
      .usingRecursiveComparison()
      .isEqualTo(springBootDependencyManagement());
    assertThat(dependencies.get(jsonWebTokenDependencyId())).isEmpty();
  }

  @Test
  void shouldGetDependenciesFromMavenFile() {
    JavaDependencies dependencies = mavenDependencies().dependencies();

    assertJJWTDependency(dependencies);
    assertLogstashDependency(dependencies);
    assertThat(dependencies.get(DependencyId.of(new GroupId("org.springdoc"), new ArtifactId("springdoc-openapi-ui")))).isEmpty();
  }

  private void assertJJWTDependency(JavaDependencies dependencies) {
    JavaDependency jjwt = dependencies
      .get(
        DependencyId.builder()
          .groupId(new GroupId("io.jsonwebtoken"))
          .artifactId(new ArtifactId("jjwt-api"))
          .classifier(new JavaDependencyClassifier("classif"))
          .build()
      )
      .orElseThrow();

    assertThat(jjwt.version()).contains(new VersionSlug("json-web-token"));
    assertThat(jjwt.scope()).isEqualTo(JavaDependencyScope.TEST);
    assertThat(jjwt.optional()).isTrue();
    assertThat(jjwt.classifier()).contains(new JavaDependencyClassifier("classif"));
  }

  private void assertLogstashDependency(JavaDependencies dependencies) {
    JavaDependency jjwt = dependencies
      .get(DependencyId.of(new GroupId("net.logstash.logback"), new ArtifactId("logstash-logback-encoder")))
      .orElseThrow();
    assertThat(jjwt.version()).isEmpty();
    assertThat(jjwt.scope()).isEqualTo(JavaDependencyScope.COMPILE);
    assertThat(jjwt.optional()).isFalse();
  }

  private ProjectJavaDependencies mavenDependencies() {
    return projectDependencies.get(new SeedProjectFolder("src/test/resources/projects/maven"));
  }
}
