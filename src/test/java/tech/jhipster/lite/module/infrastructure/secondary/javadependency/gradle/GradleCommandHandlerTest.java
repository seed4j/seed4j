package tech.jhipster.lite.module.infrastructure.secondary.javadependency.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static tech.jhipster.lite.TestFileUtils.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.javaDependency;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import java.nio.file.Paths;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

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
      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new AddDirectJavaDependency(SpringBootDependencyManager.springBootStarterWebDependency()));

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

    @EnumSource(value = JavaDependencyScope.class, mode = EXCLUDE, names = {"TEST", "RUNTIME", "PROVIDED", "IMPORT"})
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

  @Nested
  class HandleRemoveDirectJavaDependency {

    private final JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    @Test
    void shouldRemoveEntryInLibrariesSection() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);
      gradleCommandHandler.handle(new AddDirectJavaDependency(SpringBootDependencyManager.springBootStarterWebDependency()));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(SpringBootDependencyManager.springBootStarterWebDependency().id()));

      assertThat(versionCatalogContent(projectFolder))
        .doesNotContain("[libraries.spring-boot-starter-web]")
        .doesNotContain("""
          \t\tname = "spring-boot-starter-web"
          \t\tgroup = "org.springframework.boot"
          """);
    }

    @Test
    void shouldRemoveDependencyInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);
      gradleCommandHandler.handle(new AddDirectJavaDependency(SpringBootDependencyManager.springBootStarterWebDependency()));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(SpringBootDependencyManager.springBootStarterWebDependency().id()));

      assertThat(buildGradleContent(projectFolder)).doesNotContain("implementation(libs.spring.boot.starter.web)");
    }

    @Test
    void shouldRemoveTestDependencyInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);
      JavaDependency dependency = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.TEST)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependency.id()));

      assertThat(buildGradleContent(projectFolder)).doesNotContain("testImplementation(libs.junit.jupiter.engine)");
    }

    @Test
    void shouldRemoveRuntimeDependencyInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);
      JavaDependency dependency = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.RUNTIME)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependency.id()));

      assertThat(buildGradleContent(projectFolder)).doesNotContain("runtimeOnly(libs.junit.jupiter.engine)");
    }

    @Test
    void shouldRemoveProvidedDependencyInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);
      JavaDependency dependency = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.PROVIDED)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependency.id()));

      assertThat(buildGradleContent(projectFolder)).doesNotContain("compileOnly(libs.junit.jupiter.engine)");
    }
  }

  @Nested
  class HandleAddJavaDependencyManagement {

    private final JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    @Test
    void shouldAddEntryInLibrariesSectionToExistingTomlVersionCatalog() {
      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new AddJavaDependencyManagement(SpringBootDependencyManager.springBootDependencyManagement()));

      assertThat(versionCatalogContent(projectFolder))
        .contains("""
          [libraries.spring-boot-dependencies]
          \t\tname = "spring-boot-dependencies"
          \t\tgroup = "org.springframework.boot"
          """);

      assertThat(versionCatalogContent(projectFolder))
        .contains("""
          [libraries.spring-boot-dependencies.version]
          \t\t\tref = "spring-boot"
          """);
    }

    @Test
    void shouldAddImplementationDependencyInBuildGradleFileForScope() {
      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new AddJavaDependencyManagement(SpringBootDependencyManager.springBootDependencyManagement()));

      assertThat(buildGradleContent(projectFolder)).contains("implementation(platform(libs.spring.boot.dependencies))");
    }
  }

  @Nested
  class HandleRemoveJavaDependencyManagement {

    private final JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    @Test
    void shouldRemoveEntryInLibrariesSection() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);
      gradleCommandHandler.handle(new AddJavaDependencyManagement(SpringBootDependencyManager.springBootDependencyManagement()));

      gradleCommandHandler.handle(new RemoveJavaDependencyManagement(SpringBootDependencyManager.springBootDependencyManagement().id()));

      assertThat(versionCatalogContent(projectFolder))
        .doesNotContain("[libraries.spring-boot-dependencies]")
        .doesNotContain("""
          \t\tname = "spring-boot-dependencies"
          \t\tgroup = "org.springframework.boot"
          """);
    }

    @Test
    void shouldRemoveDependencyInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);
      gradleCommandHandler.handle(new AddJavaDependencyManagement(SpringBootDependencyManager.springBootDependencyManagement()));

      gradleCommandHandler.handle(new RemoveJavaDependencyManagement(SpringBootDependencyManager.springBootDependencyManagement().id()));

      assertThat(buildGradleContent(projectFolder)).doesNotContain("implementation(platform(libs.spring.boot.dependencies))");
    }
  }

  private static String buildGradleContent(JHipsterProjectFolder projectFolder) {
    return content(Paths.get(projectFolder.get()).resolve("build.gradle.kts"));
  }

  private static String versionCatalogContent(JHipsterProjectFolder projectFolder) {
    return contentNormalizingNewLines(Paths.get(projectFolder.get()).resolve("gradle/libs.versions.toml"));
  }

}
