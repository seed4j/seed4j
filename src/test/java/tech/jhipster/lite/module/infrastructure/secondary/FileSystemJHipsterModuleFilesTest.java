package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import ch.qos.logback.classic.Level;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.Logs;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.LogsSpyExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.file.JHipsterFileToMove;
import tech.jhipster.lite.module.domain.file.JHipsterFilesToDelete;
import tech.jhipster.lite.module.domain.file.JHipsterFilesToMove;
import tech.jhipster.lite.module.domain.file.JHipsterTemplatedFile;
import tech.jhipster.lite.module.domain.file.JHipsterTemplatedFiles;
import tech.jhipster.lite.module.domain.file.TemplateRenderer;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

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
