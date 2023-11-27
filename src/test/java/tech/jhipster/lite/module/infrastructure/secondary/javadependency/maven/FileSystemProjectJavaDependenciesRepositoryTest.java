package tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencies;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyClassifier;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependencies;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesVersions;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

@UnitTest
class FileSystemProjectJavaDependenciesRepositoryTest {

  private static final FileSystemProjectJavaDependenciesRepository projectDependencies = new FileSystemProjectJavaDependenciesRepository();

  @Test
  void shouldNotReadFromUnreadableMavenFile() {
    assertThatThrownBy(() -> projectDependencies.get(new JHipsterProjectFolder("src/test/resources/projects/maven-unreadable")))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldGetEmptyDependenciesFromEmptyProject() {
    assertThat(projectDependencies.get(new JHipsterProjectFolder("src/test/resources/projects/empty")))
      .isEqualTo(ProjectJavaDependencies.EMPTY);
  }

  @Test
  void shouldGetEmptyProjectDependenciesFromEmptyMavenFile() {
    assertThat(projectDependencies.get(new JHipsterProjectFolder("src/test/resources/projects/empty-maven")))
      .usingRecursiveComparison()
      .isEqualTo(ProjectJavaDependencies.EMPTY);
  }

  @Test
  void shouldGetVersionsFromMavenFile() {
    ProjectJavaDependenciesVersions versions = mavenDependencies().versions();

    assertThat(versions.get(new VersionSlug("json-web-token"))).contains(new JavaDependencyVersion("json-web-token", "0.11.5"));
    assertThat(versions.get(new VersionSlug("logstash-logback-encoder.version")))
      .contains(new JavaDependencyVersion("logstash-logback-encoder", "7.2"));
    assertThat(versions.get(new VersionSlug("dummy"))).isEmpty();
  }

  @Test
  void shouldGetDependenciesManagementFromMavenFile() {
    JavaDependencies dependencies = mavenDependencies().dependenciesManagement();

    assertThat(dependencies.get(SpringBootDependencyManager.springBootDependencyId()).get()).usingRecursiveComparison().isEqualTo(SpringBootDependencyManager.springBootDependencyManagement());
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
        DependencyId
          .builder()
          .groupId(new GroupId("io.jsonwebtoken"))
          .artifactId(new ArtifactId("jjwt-api"))
          .classifier(new JavaDependencyClassifier("classif"))
          .build()
      )
      .get();

    assertThat(jjwt.version()).contains(new VersionSlug("json-web-token"));
    assertThat(jjwt.scope()).isEqualTo(JavaDependencyScope.TEST);
    assertThat(jjwt.optional()).isTrue();
    assertThat(jjwt.classifier()).contains(new JavaDependencyClassifier("classif"));
  }

  private void assertLogstashDependency(JavaDependencies dependencies) {
    JavaDependency jjwt = dependencies
      .get(DependencyId.of(new GroupId("net.logstash.logback"), new ArtifactId("logstash-logback-encoder")))
      .get();
    assertThat(jjwt.version()).isEmpty();
    assertThat(jjwt.scope()).isEqualTo(JavaDependencyScope.COMPILE);
    assertThat(jjwt.optional()).isFalse();
  }

  private ProjectJavaDependencies mavenDependencies() {
    return projectDependencies.get(new JHipsterProjectFolder("src/test/resources/projects/maven"));
  }
}
