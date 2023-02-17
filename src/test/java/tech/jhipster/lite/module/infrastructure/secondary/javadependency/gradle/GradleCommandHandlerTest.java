package tech.jhipster.lite.module.infrastructure.secondary.javadependency.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.TestFileUtils.content;
import static tech.jhipster.lite.TestFileUtils.projectFrom;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.springBootVersion;

import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
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
  @DisplayName("Set dependency version")
  class HandleSetVersion {

    @Test
    void shouldAddVersionToMissingTomlVersionCatalogAnd() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new SetVersion(springBootVersion()));

      assertThat(versionCatalogContent(projectFolder))
        .contains("[versions]")
        .contains("spring-boot = \"1.2.3\"");
    }

    @Test
    void shouldAddVersionToExistingTomlVersionCatalog() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder).handle(new SetVersion(springBootVersion()));

      assertThat(versionCatalogContent(projectFolder))
        .contains("[versions]")
        .contains("spring-boot = \"1.2.3\"");
    }

    @Test
    void shouldUpdateExistingProperty() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(Indentation.DEFAULT, projectFolder);
      gradleCommandHandler.handle(new SetVersion(new JavaDependencyVersion("jjwt", "0.12.0")));

      gradleCommandHandler.handle(new SetVersion(new JavaDependencyVersion("jjwt", "0.13.0")));

      assertThat(versionCatalogContent(projectFolder))
        .contains("[versions]")
        .contains("jjwt = \"0.13.0\"");
    }
  }

  private static String buildGradleContent(JHipsterProjectFolder projectFolder) {
    return content(Paths.get(projectFolder.get()).resolve("build.gradle.kts"));
  }

  private static String versionCatalogContent(JHipsterProjectFolder projectFolder) {
    return content(Paths.get(projectFolder.get()).resolve("gradle/libs.versions.toml"));
  }

}
