package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.from;

import ch.qos.logback.classic.Level;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.file.JHipsterFileToMove;
import tech.jhipster.lite.module.domain.file.JHipsterFilesToDelete;
import tech.jhipster.lite.module.domain.file.JHipsterFilesToMove;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

@UnitTest
@ExtendWith(LogsSpy.class)
class FileSystemJHipsterModuleFilesTest {

  private static final FileSystemJHipsterModuleFiles files = new FileSystemJHipsterModuleFiles(new FileSystemProjectFiles());

  private final LogsSpy logs;

  public FileSystemJHipsterModuleFilesTest(LogsSpy logs) {
    this.logs = logs;
  }

  @Test
  void shouldNotWriteOnUnwritablePath() {
    JHipsterProjectFolder project = new JHipsterProjectFolder(Paths.get("src/test/resources/generator").toAbsolutePath().toString());

    JHipsterModule module = moduleBuilder(JHipsterModulesFixture.propertiesBuilder(project.folder()).build())
      .files()
      .add(from("server/springboot/core/main/MainApp.java.mustache"), to("content"))
      .and()
      .build();

    assertThatThrownBy(() -> files.create(project, module.templatedFiles())).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldTraceAddedFiles() {
    JHipsterProjectFolder project = new JHipsterProjectFolder(TestFileUtils.tmpDirForTest());

    JHipsterModule module = moduleBuilder(JHipsterModulesFixture.propertiesBuilder(project.folder()).build())
      .files()
      .add(from("server/springboot/core/main/MainApp.java.mustache"), to("MainApp.java"))
      .and()
      .build();

    files.create(project, module.templatedFiles());

    logs.shouldHave(Level.DEBUG, "MainApp.java");
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
    Path folderPath = Paths.get(folder);
    JHipsterProjectFolder project = new JHipsterProjectFolder(folder);
    Files.createDirectories(folderPath);
    Files.copy(Paths.get("src/test/resources/projects/files/dummy.txt"), folderPath.resolve("dummy.txt"));

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
