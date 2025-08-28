package com.seed4j.project.infrastructure.secondary;

import static com.seed4j.project.domain.history.ProjectHistoryFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seed4j.JsonHelper;
import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.download.Project;
import com.seed4j.project.domain.download.ProjectName;
import com.seed4j.project.domain.history.ProjectHistory;
import com.seed4j.shared.error.domain.GeneratorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class FileSystemProjectsRepositoryTest {

  private static final FileSystemProjectsRepository projects = new FileSystemProjectsRepository(JsonHelper.jsonMapper());

  @Nested
  @DisplayName("Download")
  class FileSystemProjectsRepositoryDownloadTest {

    @Test
    void shouldGetEmptyProjectFromUnknownFolder() {
      assertThat(projects.get(new ProjectPath(TestFileUtils.tmpDirForTest()))).isEmpty();
    }

    @Test
    void shouldGetEmptyProjectFromEmptyFolder() throws IOException {
      String folder = TestFileUtils.tmpDirForTest();
      Files.createDirectories(Path.of(folder));

      assertThat(projects.get(new ProjectPath(folder))).isEmpty();
    }

    @Test
    void shouldGetEmptyProjectFromFile() {
      ProjectPath path = folder().add("src/test/resources/projects/maven/pom.xml").build();

      ProjectPath filePath = new ProjectPath(path.get() + "/pom.xml");

      assertThat(projects.get(filePath)).isEmpty();
    }

    @Test
    void shouldGetZippedProjectFromFolderWithoutPackageJson() {
      ProjectPath path = folder().add("src/test/resources/projects/maven/pom.xml").build();

      Project project = projects.get(path).orElseThrow();

      assertThat(project.name()).isEqualTo(ProjectName.DEFAULT);
      assertThat(zippedFiles(project.content())).containsExactly("pom.xml");
    }

    @Test
    void shouldGetZippedProjectFromFolderEmptyPackageJson() {
      ProjectPath path = folder().add("src/test/resources/projects/empty-package-json/package.json").build();

      Project project = projects.get(path).orElseThrow();

      assertThat(project.name()).isEqualTo(ProjectName.DEFAULT);
      assertThat(zippedFiles(project.content())).containsExactly("package.json");
    }

    @Test
    void shouldGetZippedProjectFromFolderPackageJsonWithProjectName() {
      ProjectPath path = folder().add("src/test/resources/projects/package-json/package.json").build();

      Project project = projects.get(path).orElseThrow();

      assertThat(project.name()).isEqualTo(new ProjectName("seed4j-project"));
      assertThat(zippedFiles(project.content())).containsExactly("package.json");
    }

    @Test
    void shouldNotGetNodeModulesInZipFile() {
      ProjectPath path = folder()
        .add("src/test/resources/projects/node/package.json", "beer.json")
        .add("src/test/resources/projects/node/package.json", "dummy/beer.json")
        .add("src/test/resources/projects/node/package.json", "node_modules/package.json")
        .build();

      Project project = projects.get(path).orElseThrow();

      assertThat(zippedFiles(project.content()))
        .doesNotContain("node_modules", "node_modules/package.json")
        .contains("beer.json", "dummy" + FileSystems.getDefault().getSeparator() + "beer.json");
    }

    private Collection<String> zippedFiles(byte[] content) {
      try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(content))) {
        ZipEntry entry = zip.getNextEntry();

        List<String> result = new ArrayList<>();
        while (entry != null) {
          result.add(entry.getName());

          entry = zip.getNextEntry();
        }

        return result;
      } catch (IOException e) {
        throw new AssertionError(e);
      }
    }
  }

  @Nested
  @DisplayName("Save history")
  class FileSystemProjectsRepositorySaveHistoryTest {

    @Test
    void shouldHandleSerializationError() throws JsonProcessingException {
      ObjectWriter writer = mock(ObjectWriter.class);
      when(writer.writeValueAsBytes(any())).thenThrow(JsonProcessingException.class);

      ObjectMapper json = mock(ObjectMapper.class);
      when(json.writerWithDefaultPrettyPrinter()).thenReturn(writer);

      FileSystemProjectsRepository fileSystemProjectsRepository = new FileSystemProjectsRepository(json);

      assertThatThrownBy(() -> fileSystemProjectsRepository.save(projectPath(), projectAction())).isExactlyInstanceOf(
        GeneratorException.class
      );
    }

    @Test
    void shouldSaveHistory() throws IOException {
      ProjectPath path = folder().build();

      projects.save(path, projectAction());

      try (Stream<Path> files = Files.list(Path.of(path.get(), ".seed4j/modules"))) {
        Path filepath = files.findFirst().orElseThrow();
        assertThat(filepath.getFileName().toString()).startsWith("20").endsWith("-test-module.json");
        assertThat(Files.readString(filepath)).isEqualToIgnoringWhitespace(
            """
            {
              "module" : "test-module",
              "date" : "2021-12-03T10:15:30Z",
              "properties" : {
                "key" : "value"
              }
            }
            """
          );
      }
    }
  }

  @Nested
  @DisplayName("Get history")
  class FileSystemProjectsRepositoryGetHistoryTest {

    @Test
    void shouldHandleDeserializationErrors() throws IOException {
      ProjectPath path = folder().add("src/test/resources/projects/history/history.json", ".seed4j/modules/history.json").build();
      ObjectMapper json = mock(ObjectMapper.class);
      when(json.readValue(any(byte[].class), eq(PersistedProjectHistory.class))).thenThrow(IOException.class);

      FileSystemProjectsRepository fileSystemProjectsRepository = new FileSystemProjectsRepository(json);

      assertThatThrownBy(() -> fileSystemProjectsRepository.getHistory(path)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldGetEmptyHistoryFromUnknownFiles() {
      ProjectPath path = folder().build();

      ProjectHistory history = projects.getHistory(path);

      assertThat(history.path()).isEqualTo(path);
      assertThat(history.actions()).isEmpty();
    }

    @Test
    void shouldGetExistingHistory() {
      ProjectPath path = folder().add("src/test/resources/projects/history/history.json", ".seed4j/modules/history.json").build();

      ProjectHistory history = projects.getHistory(path);

      assertThat(history.path()).isEqualTo(path);
      assertThat(history.actions()).usingRecursiveFieldByFieldElementComparator().containsExactly(projectAction());
    }

    @Test
    void shouldGetExistingHistoryFromOneActionFiles() {
      ProjectPath path = folder()
        .add(
          "src/test/resources/projects/composed-history/20250819203000-another-test-module.json",
          ".seed4j/modules/20250819203000-another-test-module.json"
        )
        .build();

      ProjectHistory history = projects.getHistory(path);

      assertThat(history.actions()).usingRecursiveFieldByFieldElementComparator().containsExactly(firstProjectAction());
    }

    @Test
    void shouldGetExistingHistoryFromMultipleFiles() {
      ProjectPath path = folder()
        .add("src/test/resources/projects/composed-history/history.json", ".seed4j/modules/history.json")
        .add(
          "src/test/resources/projects/composed-history/20250819203000-another-test-module.json",
          ".seed4j/modules/20250819203000-another-test-module.json"
        )
        .add(
          "src/test/resources/projects/composed-history/20250819203001-another-test-module.json",
          ".seed4j/modules/20250819203001-another-test-module.json"
        )
        .add("src/test/resources/projects/composed-history/dummy.txt", ".seed4j/modules/dummy.txt")
        .build();

      ProjectHistory history = projects.getHistory(path);

      assertThat(history.actions())
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactly(projectAction(), firstProjectAction(), secondProjectAction());
    }
  }

  private static FolderBuilder folder() {
    return new FolderBuilder();
  }

  private static final class FolderBuilder {

    private final Path folder;

    private FolderBuilder() {
      folder = Path.of(TestFileUtils.tmpDirForTest());

      createFolder();
    }

    private void createFolder() throws AssertionError {
      try {
        Files.createDirectories(folder);
      } catch (IOException e) {
        throw new AssertionError(e);
      }
    }

    private FolderBuilder add(String source) {
      return add(source, Path.of(source).getFileName().toString());
    }

    private FolderBuilder add(String source, String destination) {
      Path sourcePath = Path.of(source);

      try {
        Path destinationPath = folder.resolve(destination);
        Files.createDirectories(destinationPath.getParent());
        Files.copy(sourcePath, destinationPath);
      } catch (IOException e) {
        throw new AssertionError(e);
      }

      return this;
    }

    private ProjectPath build() {
      return new ProjectPath(folder.toString());
    }
  }
}
