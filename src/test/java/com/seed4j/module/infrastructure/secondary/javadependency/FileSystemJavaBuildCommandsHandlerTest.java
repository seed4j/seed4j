package com.seed4j.module.infrastructure.secondary.javadependency;

import static com.seed4j.TestFileUtils.content;
import static com.seed4j.TestFileUtils.projectFrom;
import static com.seed4j.module.domain.SeedModulesFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.module.infrastructure.secondary.*;
import com.seed4j.module.infrastructure.secondary.javabuild.FileSystemProjectJavaBuildToolRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class FileSystemJavaBuildCommandsHandlerTest {

  private static final FileSystemJavaBuildCommandsHandler handler = new FileSystemJavaBuildCommandsHandler(
    new FileSystemProjectJavaBuildToolRepository(),
    new FileSystemSeedModuleFiles(new FileSystemProjectFiles(), TemplateRenderer.NOOP),
    new FileSystemReplacer(TemplateRenderer.NOOP)
  );

  @Test
  void shouldNotTryToHandleEmptyCommands() {
    assertThatCode(() ->
      handler.handle(
        Indentation.DEFAULT,
        projectFrom("src/test/resources/projects/empty"),
        emptyModuleContext(),
        new JavaBuildCommands(null)
      )
    ).doesNotThrowAnyException();
  }

  @Test
  void shouldNotHandleCommandsOnProjectWithoutMavenOrGradle() {
    assertThatThrownBy(() ->
      handler.handle(
        Indentation.DEFAULT,
        projectFrom("src/test/resources/projects/empty"),
        emptyModuleContext(),
        javaDependenciesCommands()
      )
    ).isExactlyInstanceOf(MissingJavaBuildConfigurationException.class);
  }

  @Test
  void shouldHandleCommandsWithMavenOnProjectWithPom() {
    SeedProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-maven");
    handler.handle(
      Indentation.DEFAULT,
      projectFolder,
      emptyModuleContext(),
      new JavaBuildCommands(List.of(new SetVersion(springBootVersion())))
    );
    assertThat(content(projectFolder.filePath("pom.xml"))).contains("<spring-boot.version>1.2.3</spring-boot.version>");
  }

  @Test
  void shouldHandleCommandsWithGradleOnProjectWithBuildGradle() {
    SeedProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
    handler.handle(
      Indentation.DEFAULT,
      projectFolder,
      emptyModuleContext(),
      new JavaBuildCommands(List.of(new SetVersion(springBootVersion())))
    );
    assertThat(content(projectFolder.filePath("gradle/libs.versions.toml"))).contains("spring-boot = \"1.2.3\"");
  }
}
