package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.JHipsterModule.*;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.module.domain.file.JHipsterFileToMove;
import com.seed4j.module.domain.file.JHipsterFilesToDelete;
import com.seed4j.module.domain.file.JHipsterFilesToMove;
import com.seed4j.module.domain.file.JHipsterTemplatedFile;
import com.seed4j.module.domain.file.JHipsterTemplatedFiles;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class FileSystemJHipsterModuleFilesTest {

  private static final FileSystemJHipsterModuleFiles files = new FileSystemJHipsterModuleFiles(
    new FileSystemProjectFiles(),
    TemplateRenderer.NOOP
  );

  @Logs
  private LogsSpy logs;

  @Test
  void shouldNotWriteOnUnwritablePath() {
    JHipsterProjectFolder project = new JHipsterProjectFolder(Path.of("src/test/resources/generator").toAbsolutePath().toString());

    JHipsterModule module = moduleBuilder(JHipsterModulesFixture.propertiesBuilder(project.folder()).build())
      .files()
      .add(from("server/springboot/core/main/MainApp.java.mustache"), to("content"))
      .and()
      .build();

    assertThatThrownBy(() -> files.create(project, templatedFilesFrom(module))).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldTraceAddedFiles() {
    JHipsterProjectFolder project = new JHipsterProjectFolder(TestFileUtils.tmpDirForTest());

    JHipsterModule module = moduleBuilder(JHipsterModulesFixture.propertiesBuilder(project.folder()).build())
      .files()
      .add(from("server/springboot/core/main/MainApp.java.mustache"), to("MainApp.java"))
      .and()
      .build();

    files.create(project, templatedFilesFrom(module));

    logs.shouldHave(Level.DEBUG, "MainApp.java");
  }

  @NotNull
  private static JHipsterTemplatedFiles templatedFilesFrom(JHipsterModule module) {
    Assert.notEmpty("module.filesToAdd", module.filesToAdd());
    return new JHipsterTemplatedFiles(
      List.of(JHipsterTemplatedFile.builder().file(module.filesToAdd().iterator().next()).context(context()).build())
    );
  }

  @Test
  void shouldNotMoveUnknownFile() {
    JHipsterProjectFolder project = new JHipsterProjectFolder(TestFileUtils.tmpDirForTest());

    assertThatThrownBy(() ->
      files.move(
        project,
        new JHipsterFilesToMove(List.of(new JHipsterFileToMove(new JHipsterProjectFilePath("unknown-file"), to("dummy"))))
      )
    )
      .isExactlyInstanceOf(UnknownFileToMoveException.class)
      .hasMessageContaining("unknown-file");
  }

  @Test
  void shouldTraceNotMoveFileWithExistingDestination() throws IOException {
    String folder = TestFileUtils.tmpDirForTest();
    Path folderPath = Path.of(folder);
    JHipsterProjectFolder project = new JHipsterProjectFolder(folder);
    Files.createDirectories(folderPath);
    Files.copy(Path.of("src/test/resources/projects/files/dummy.txt"), folderPath.resolve("dummy.txt"));

    files.move(project, new JHipsterFilesToMove(List.of(new JHipsterFileToMove(new JHipsterProjectFilePath("file"), to("dummy.txt")))));

    logs.shouldHave(Level.INFO, "dummy.txt");
  }

  @Test
  void shouldNotDeleteUnknownFile() {
    JHipsterProjectFolder project = new JHipsterProjectFolder(TestFileUtils.tmpDirForTest());

    assertThatThrownBy(() -> files.delete(project, new JHipsterFilesToDelete(List.of(new JHipsterProjectFilePath("unknown-file")))))
      .isExactlyInstanceOf(UnknownFileToDeleteException.class)
      .hasMessageContaining("unknown-file");
  }
}
