package com.seed4j.module.infrastructure.secondary.javadependency;

import static com.seed4j.TestFileUtils.content;
import static com.seed4j.TestFileUtils.projectFrom;
import static com.seed4j.module.domain.Seed4JModulesFixture.emptyModuleContext;
import static com.seed4j.module.domain.Seed4JModulesFixture.javaDependenciesCommands;
import static com.seed4j.module.domain.Seed4JModulesFixture.springBootVersion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.infrastructure.secondary.FileSystemProjectFiles;
import com.seed4j.module.infrastructure.secondary.FileSystemReplacer;
import com.seed4j.module.infrastructure.secondary.FileSystemSeed4JModuleFiles;
import com.seed4j.module.infrastructure.secondary.javabuild.FileSystemProjectJavaBuildToolRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class FileSystemJavaBuildCommandsHandlerTest {

  private static final FileSystemJavaBuildCommandsHandler handler = new FileSystemJavaBuildCommandsHandler(
    new FileSystemProjectJavaBuildToolRepository(),
    new FileSystemSeed4JModuleFiles(new FileSystemProjectFiles(), TemplateRenderer.NOOP),
    new FileSystemReplacer(TemplateRenderer.NOOP)
  );

  @Test
  void shouldNotTryToHandleEmptyCommands() {
    assertThatCode(() ->
      handler.handle(
        Indentation.DEFAULT,
        projectFrom("src/test/resources/projects/empty"),
        emptyModuleContext(),
        new JavaBuildCommands(List.of())
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
    Seed4JProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-maven");
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
    Seed4JProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
    handler.handle(
      Indentation.DEFAULT,
      projectFolder,
      emptyModuleContext(),
      new JavaBuildCommands(List.of(new SetVersion(springBootVersion())))
    );
    assertThat(content(projectFolder.filePath("gradle/libs.versions.toml"))).contains("spring-boot = \"1.2.3\"");
  }
}
