package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.Seed4JModule.*;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModulesFixture.*;
import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.file.Seed4JFileToMove;
import com.seed4j.module.domain.file.Seed4JFilesToDelete;
import com.seed4j.module.domain.file.Seed4JFilesToMove;
import com.seed4j.module.domain.file.Seed4JTemplatedFile;
import com.seed4j.module.domain.file.Seed4JTemplatedFiles;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
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
class FileSystemSeed4JModuleFilesTest {

  private static final FileSystemSeed4JModuleFiles files = new FileSystemSeed4JModuleFiles(
    new FileSystemProjectFiles(),
    TemplateRenderer.NOOP
  );

  @Logs
  private LogsSpy logs;

  @Test
  void shouldNotWriteOnUnwritablePath() {
    Seed4JProjectFolder project = new Seed4JProjectFolder(Path.of("src/test/resources/generator").toAbsolutePath().toString());

    Seed4JModule module = moduleBuilder(Seed4JModulesFixture.propertiesBuilder(project.folder()).build())
      .files()
      .add(from("server/springboot/core/main/MainApp.java.mustache"), to("content"))
      .and()
      .build();

    assertThatThrownBy(() -> files.create(project, templatedFilesFrom(module))).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldTraceAddedFiles() {
    Seed4JProjectFolder project = new Seed4JProjectFolder(TestFileUtils.tmpDirForTest());

    Seed4JModule module = moduleBuilder(Seed4JModulesFixture.propertiesBuilder(project.folder()).build())
      .files()
      .add(from("server/springboot/core/main/MainApp.java.mustache"), to("MainApp.java"))
      .and()
      .build();

    files.create(project, templatedFilesFrom(module));

    logs.shouldHave(Level.DEBUG, "MainApp.java");
  }

  @NotNull
  private static Seed4JTemplatedFiles templatedFilesFrom(Seed4JModule module) {
    Assert.notEmpty("module.filesToAdd", module.filesToAdd());
    return new Seed4JTemplatedFiles(
      List.of(Seed4JTemplatedFile.builder().file(module.filesToAdd().iterator().next()).context(context()).build())
    );
  }

  @Test
  void shouldNotMoveUnknownFile() {
    Seed4JProjectFolder project = new Seed4JProjectFolder(TestFileUtils.tmpDirForTest());

    assertThatThrownBy(() ->
      files.move(project, new Seed4JFilesToMove(List.of(new Seed4JFileToMove(new Seed4JProjectFilePath("unknown-file"), to("dummy")))))
    )
      .isExactlyInstanceOf(UnknownFileToMoveException.class)
      .hasMessageContaining("unknown-file");
  }

  @Test
  void shouldTraceNotMoveFileWithExistingDestination() throws IOException {
    String folder = TestFileUtils.tmpDirForTest();
    Path folderPath = Path.of(folder);
    Seed4JProjectFolder project = new Seed4JProjectFolder(folder);
    Files.createDirectories(folderPath);
    Files.copy(Path.of("src/test/resources/projects/files/dummy.txt"), folderPath.resolve("dummy.txt"));

    files.move(project, new Seed4JFilesToMove(List.of(new Seed4JFileToMove(new Seed4JProjectFilePath("file"), to("dummy.txt")))));

    logs.shouldHave(Level.INFO, "dummy.txt");
  }

  @Test
  void shouldNotDeleteUnknownFile() {
    Seed4JProjectFolder project = new Seed4JProjectFolder(TestFileUtils.tmpDirForTest());

    assertThatThrownBy(() -> files.delete(project, new Seed4JFilesToDelete(List.of(new Seed4JProjectFilePath("unknown-file")))))
      .isExactlyInstanceOf(UnknownFileToDeleteException.class)
      .hasMessageContaining("unknown-file");
  }
}
