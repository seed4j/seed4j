package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static com.seed4j.module.domain.SeedModule.*;
import static com.seed4j.module.domain.SeedModule.from;
import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedProjectFilePath;
import com.seed4j.module.domain.file.SeedFileToMove;
import com.seed4j.module.domain.file.SeedFilesToDelete;
import com.seed4j.module.domain.file.SeedFilesToMove;
import com.seed4j.module.domain.file.SeedTemplatedFile;
import com.seed4j.module.domain.file.SeedTemplatedFiles;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.properties.SeedProjectFolder;
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
class FileSystemSeedModuleFilesTest {

  private static final FileSystemJHipsterModuleFiles files = new FileSystemJHipsterModuleFiles(
    new FileSystemProjectFiles(),
    TemplateRenderer.NOOP
  );

  @Logs
  private LogsSpy logs;

  @Test
  void shouldNotWriteOnUnwritablePath() {
    SeedProjectFolder project = new SeedProjectFolder(Path.of("src/test/resources/generator").toAbsolutePath().toString());

    SeedModule module = moduleBuilder(JHipsterModulesFixture.propertiesBuilder(project.folder()).build())
      .files()
      .add(from("server/springboot/core/main/MainApp.java.mustache"), to("content"))
      .and()
      .build();

    assertThatThrownBy(() -> files.create(project, templatedFilesFrom(module))).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldTraceAddedFiles() {
    SeedProjectFolder project = new SeedProjectFolder(TestFileUtils.tmpDirForTest());

    SeedModule module = moduleBuilder(JHipsterModulesFixture.propertiesBuilder(project.folder()).build())
      .files()
      .add(from("server/springboot/core/main/MainApp.java.mustache"), to("MainApp.java"))
      .and()
      .build();

    files.create(project, templatedFilesFrom(module));

    logs.shouldHave(Level.DEBUG, "MainApp.java");
  }

  @NotNull
  private static SeedTemplatedFiles templatedFilesFrom(SeedModule module) {
    Assert.notEmpty("module.filesToAdd", module.filesToAdd());
    return new SeedTemplatedFiles(
      List.of(SeedTemplatedFile.builder().file(module.filesToAdd().iterator().next()).context(context()).build())
    );
  }

  @Test
  void shouldNotMoveUnknownFile() {
    SeedProjectFolder project = new SeedProjectFolder(TestFileUtils.tmpDirForTest());

    assertThatThrownBy(() ->
      files.move(project, new SeedFilesToMove(List.of(new SeedFileToMove(new SeedProjectFilePath("unknown-file"), to("dummy")))))
    )
      .isExactlyInstanceOf(UnknownFileToMoveException.class)
      .hasMessageContaining("unknown-file");
  }

  @Test
  void shouldTraceNotMoveFileWithExistingDestination() throws IOException {
    String folder = TestFileUtils.tmpDirForTest();
    Path folderPath = Path.of(folder);
    SeedProjectFolder project = new SeedProjectFolder(folder);
    Files.createDirectories(folderPath);
    Files.copy(Path.of("src/test/resources/projects/files/dummy.txt"), folderPath.resolve("dummy.txt"));

    files.move(project, new SeedFilesToMove(List.of(new SeedFileToMove(new SeedProjectFilePath("file"), to("dummy.txt")))));

    logs.shouldHave(Level.INFO, "dummy.txt");
  }

  @Test
  void shouldNotDeleteUnknownFile() {
    SeedProjectFolder project = new SeedProjectFolder(TestFileUtils.tmpDirForTest());

    assertThatThrownBy(() -> files.delete(project, new SeedFilesToDelete(List.of(new SeedProjectFilePath("unknown-file")))))
      .isExactlyInstanceOf(UnknownFileToDeleteException.class)
      .hasMessageContaining("unknown-file");
  }
}
