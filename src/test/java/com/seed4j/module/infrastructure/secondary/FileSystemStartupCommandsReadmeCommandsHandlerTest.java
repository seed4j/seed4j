package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.*;
import static com.seed4j.module.domain.Seed4JModulesFixture.emptyModuleContext;
import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.domain.startupcommand.*;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class FileSystemStartupCommandsReadmeCommandsHandlerTest {

  private static final FileSystemStartupCommandsReadmeCommandsHandler handler = new FileSystemStartupCommandsReadmeCommandsHandler(
    new FileSystemReplacer(TemplateRenderer.NOOP)
  );

  @Logs
  private LogsSpy logs;

  @Test
  void shouldAddMavenCommandToReadme() {
    Seed4JProjectFolder projectFolder = projectFolderWithReadme();
    Seed4JStartupCommand command = new MavenStartupCommandLine("clean verify sonar:sonar");

    handler.handle(projectFolder, new Seed4JStartupCommands(List.of(command)), emptyModuleContext());

    assertThat(content(projectFolder.filePath("README.md"))).contains("./mvnw clean verify sonar:sonar");
  }

  @Test
  void shouldNotAddMavenCommandToMavenProjectWithoutReadme() {
    Seed4JProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");
    Seed4JStartupCommand command = new MavenStartupCommandLine("clean verify sonar:sonar");

    handler.handle(projectFolder, new Seed4JStartupCommands(List.of(command)), emptyModuleContext());

    logs.shouldHave(Level.DEBUG, "Can't apply optional replacement: ");
  }

  @Test
  void shouldAddGradleCommandToReadme() {
    Seed4JProjectFolder projectFolder = projectFolderWithReadme();
    Seed4JStartupCommand command = new GradleStartupCommandLine("clean build sonar --info");

    handler.handle(projectFolder, new Seed4JStartupCommands(List.of(command)), emptyModuleContext());

    assertThat(content(projectFolder.filePath("README.md"))).contains("./gradlew clean build sonar --info");
  }

  @Test
  void shouldAddDockerComposeCommandToReadme() {
    Seed4JProjectFolder projectFolder = projectFolderWithReadme();
    Seed4JStartupCommand command = new DockerComposeStartupCommandLine(new DockerComposeFile("src/main/docker/sonar.yml"));

    handler.handle(projectFolder, new Seed4JStartupCommands(List.of(command)), emptyModuleContext());

    assertThat(content(projectFolder.filePath("README.md"))).contains("docker compose -f src/main/docker/sonar.yml up -d");
  }

  @ParameterizedTest
  @MethodSource("commandOrderScenarios")
  void shouldAddCommandsToProjectReadmeRespectingInsertOrder(List<Seed4JStartupCommand> commands, String expectedReadmeContent) {
    Seed4JProjectFolder projectFolder = projectFolderWithReadme();

    handler.handle(projectFolder, new Seed4JStartupCommands(commands), emptyModuleContext());

    assertThat(content(projectFolder.filePath("README.md"))).contains(expectedReadmeContent);
  }

  private Seed4JProjectFolder projectFolderWithReadme() {
    Seed4JProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");
    copy(Path.of("src/test/resources/projects/README.md"), projectFolder.filePath("README.md"));
    return projectFolder;
  }

  private static Stream<Arguments> commandOrderScenarios() {
    Seed4JStartupCommand mavenCommand = new MavenStartupCommandLine("clean verify sonar:sonar");
    Seed4JStartupCommand mavenCommandEmpty = new MavenStartupCommandLine("");
    Seed4JStartupCommand gradleCommandEmpty = new GradleStartupCommandLine("");
    Seed4JStartupCommand dockerComposeCommand = new DockerComposeStartupCommandLine(new DockerComposeFile("src/main/docker/sonar.yml"));

    return Stream.of(
      Arguments.of(
        List.of(mavenCommand, mavenCommandEmpty, gradleCommandEmpty, dockerComposeCommand),
        """
        ```bash
        ./mvnw clean verify sonar:sonar
        ```

        ```bash
        ./mvnw
        ```

        ```bash
        ./gradlew
        ```

        ```bash
        docker compose -f src/main/docker/sonar.yml up -d
        ```
        """
      ),
      Arguments.of(
        List.of(dockerComposeCommand, mavenCommand),
        """
        ```bash
        docker compose -f src/main/docker/sonar.yml up -d
        ```

        ```bash
        ./mvnw clean verify sonar:sonar
        ```
        """
      )
    );
  }
}
