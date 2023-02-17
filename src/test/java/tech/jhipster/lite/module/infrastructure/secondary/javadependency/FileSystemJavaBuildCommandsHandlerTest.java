package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.TestFileUtils.content;
import static tech.jhipster.lite.TestFileUtils.projectFrom;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.javaDependenciesCommands;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.springBootVersion;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class FileSystemJavaBuildCommandsHandlerTest {

  private static final FileSystemJavaBuildCommandsHandler handler = new FileSystemJavaBuildCommandsHandler();

  @Test
  void shouldNotTryToHandleEmptyCommands() {
    assertThatCode(() -> handler.handle(Indentation.DEFAULT, projectFrom("src/test/resources/projects/empty"), new JavaBuildCommands(null)))
      .doesNotThrowAnyException();
  }

  @Test
  void shouldNotHandleCommandsOnProjectWithoutMavenOrGradle() {
    assertThatThrownBy(() ->
        handler.handle(Indentation.DEFAULT, projectFrom("src/test/resources/projects/empty"), javaDependenciesCommands())
      )
      .isExactlyInstanceOf(MissingJavaBuildConfigurationException.class);
  }

  @Test
  void shouldHandleCommandsWithMavenOnProjectWithPom() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-maven");
    handler.handle(Indentation.DEFAULT, projectFolder, new JavaBuildCommands(List.of(new SetVersion(springBootVersion()))));
    assertThat(content(projectFolder.filePath("pom.xml"))).contains("<spring-boot.version>1.2.3</spring-boot.version>");
  }

  @Test
  void shouldHandleCommandsWithGradleOnProjectWithBuildGradle() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
    handler.handle(Indentation.DEFAULT, projectFolder, new JavaBuildCommands(List.of(new SetVersion(springBootVersion()))));
    assertThat(content(projectFolder.filePath("gradle/libs.versions.toml"))).contains("spring-boot = \"1.2.3\"");
  }
}
