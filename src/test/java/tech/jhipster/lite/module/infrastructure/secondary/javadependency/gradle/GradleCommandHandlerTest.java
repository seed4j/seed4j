package tech.jhipster.lite.module.infrastructure.secondary.javadependency.gradle;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static tech.jhipster.lite.TestFileUtils.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.javaDependency;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import java.nio.file.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import tech.jhipster.lite.*;
import tech.jhipster.lite.module.domain.*;
import tech.jhipster.lite.module.domain.javabuild.command.*;
import tech.jhipster.lite.module.domain.javadependency.*;
import tech.jhipster.lite.module.domain.properties.*;

@UnitTest
class GradleCommandHandlerTest {

  @Test
  void shouldHandleInvalidTomlVersionCatalog() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-unreadable");

    assertThatThrownBy(() -> new GradleCommandHandler(Indentation.DEFAULT, projectFolder))
      .isExactlyInstanceOf(InvalidTomlVersionCatalogException.class);
  }

  @Nested
  class HandleSetVersion {

    @Test
    void shouldAddVersionToMissingTomlVersionCatalogAnd() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new SetVersion(springBootVersion()));

      assertThat(versionCatalogContent(projectFolder))
        .contains("""
          [versions]
          \tspring-boot = "1.2.3"
          """);
    }

    @Test
    void shouldAddVersionToExistingTomlVersionCatalog() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new SetVersion(springBootVersion()));

      assertThat(versionCatalogContent(projectFolder))
        .contains("""
          [versions]
          \tspring-boot = "1.2.3"
          """);
    }

    @Test
    void shouldUpdateExistingProperty() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);
      gradleCommandHandler.handle(new SetVersion(new JavaDependencyVersion("jjwt", "0.12.0")));

      gradleCommandHandler.handle(new SetVersion(new JavaDependencyVersion("jjwt", "0.13.0")));

      assertThat(versionCatalogContent(projectFolder))
        .contains("""
          [versions]
          \tjjwt = "0.13.0"
          """);
    }
  }

  @Nested
  class HandleAddDirectJavaDependency {

    private final JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    @Test
    void shouldAddEntryInLibrariesSectionToExistingTomlVersionCatalog() {
      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new AddDirectJavaDependency(springBootStarterWebDependency()));

      assertThat(versionCatalogContent(projectFolder))
        .contains("""
          [libraries.spring-boot-starter-web]
          \t\tname = "spring-boot-starter-web"
          \t\tgroup = "org.springframework.boot"
          """);
    }

    @Test
    void shouldUpdateEntryInLibrariesSectionToExistingTomlVersionCatalog() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);
      JavaDependency initialDependency = javaDependency()
        .groupId("org.springframework.boot")
        .artifactId("spring-boot-starter-web")
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(initialDependency));

      JavaDependency updatedDependency = javaDependency()
        .groupId("org.spring.boot")
        .artifactId("spring-boot-starter-web")
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(updatedDependency));

      assertThat(versionCatalogContent(projectFolder))
        .contains("""
          [libraries.spring-boot-starter-web]
          \t\tname = "spring-boot-starter-web"
          \t\tgroup = "org.spring.boot"
          """);
    }

    @Test
    void shouldIncludeVersionRefInLibrariesSectionOfTomlVersionCatalog() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);

      JavaDependency dependency = javaDependency()
        .groupId("org.spring.boot")
        .artifactId("spring-boot-starter-web")
        .versionSlug("spring-boot")
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency));

      assertThat(versionCatalogContent(projectFolder))
        .contains("""
          [libraries.spring-boot-starter-web.version]
          \t\t\tref = "spring-boot"
          """);
    }

    @EnumSource(value = JavaDependencyScope.class, mode = EXCLUDE, names = {"TEST", "RUNTIME", "PROVIDED"})
    @ParameterizedTest
    void shouldAddImplementationDependencyInBuildGradleFileForScope(JavaDependencyScope scope) {
      JavaDependency dependency = javaDependency()
        .groupId("org.spring.boot")
        .artifactId("spring-boot-starter-web")
        .scope(scope)
        .build();
      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new AddDirectJavaDependency(dependency));

      assertThat(buildGradleContent(projectFolder)).contains("implementation(libs.spring.boot.starter.web)");
    }

    @Test
    void shouldAddRuntimeOnlyDependencyInBuildGradleFileForRuntimeScope() {
      JavaDependency dependency = javaDependency()
        .groupId("org.spring.boot")
        .artifactId("spring-boot-starter-web")
        .scope(JavaDependencyScope.RUNTIME)
        .build();
      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new AddDirectJavaDependency(dependency));

      assertThat(buildGradleContent(projectFolder)).contains("runtimeOnly(libs.spring.boot.starter.web)");
    }

    @Test
    void shouldAddCompileOnlyDependencyInBuildGradleFileForProvidedScope() {
      JavaDependency dependency = javaDependency()
        .groupId("org.spring.boot")
        .artifactId("spring-boot-starter-web")
        .scope(JavaDependencyScope.PROVIDED)
        .build();
      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new AddDirectJavaDependency(dependency));

      assertThat(buildGradleContent(projectFolder)).contains("compileOnly(libs.spring.boot.starter.web)");
    }

    @Test
    void shouldAddTestImplementationDependencyInBuildGradleFileForTestScope() {
      JavaDependency dependency = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.TEST)
        .build();
      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new AddDirectJavaDependency(dependency));

      assertThat(buildGradleContent(projectFolder)).contains("testImplementation(libs.junit.jupiter.engine)");
    }
  }

  private static String buildGradleContent(JHipsterProjectFolder projectFolder) {
    return content(Paths.get(projectFolder.get()).resolve("build.gradle.kts"));
  }

  private static String versionCatalogContent(JHipsterProjectFolder projectFolder) {
    return content(Paths.get(projectFolder.get()).resolve("gradle/libs.versions.toml")).replace("\r\n", "\n");
  }

}
