package com.seed4j.history.infrastructure.secondary;

import static com.seed4j.history.domain.ProjectHistoryFixture.firstProjectAction;
import static com.seed4j.history.domain.ProjectHistoryFixture.projectAction;
import static com.seed4j.history.domain.ProjectHistoryFixture.projectPath;
import static com.seed4j.history.domain.ProjectHistoryFixture.secondProjectAction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.seed4j.JsonHelper;
import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.history.domain.ProjectHistory;
import com.seed4j.history.domain.ProjectPath;
import com.seed4j.shared.error.domain.GeneratorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

@UnitTest
class FileSystemProjectHistoryRepositoryTest {

  private static final FileSystemProjectHistoryRepository histories = new FileSystemProjectHistoryRepository(JsonHelper.jsonMapper());

  @Nested
  @DisplayName("Save history")
  class FileSystemProjectHistoryRepositorySaveTest {

    @Test
    void shouldHandleSerializationError() throws JacksonException {
      ObjectWriter writer = mock(ObjectWriter.class);
      when(writer.writeValueAsBytes(any())).thenThrow(JacksonException.class);

      ObjectMapper json = mock(ObjectMapper.class);
      when(json.writerWithDefaultPrettyPrinter()).thenReturn(writer);

      FileSystemProjectHistoryRepository repository = new FileSystemProjectHistoryRepository(json);

      assertThatThrownBy(() -> repository.save(projectPath(), projectAction())).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldSaveHistory() throws IOException {
      ProjectPath path = folder().build();

      histories.save(path, projectAction());

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
  class FileSystemProjectHistoryRepositoryGetTest {

    @Test
    void shouldHandleDeserializationErrors() {
      ProjectPath path = folder().add("src/test/resources/projects/history/history.json", ".seed4j/modules/history.json").build();
      ObjectMapper json = mock(ObjectMapper.class);
      when(json.readValue(any(byte[].class), eq(PersistedProjectHistory.class))).thenThrow(JacksonException.class);

      FileSystemProjectHistoryRepository repository = new FileSystemProjectHistoryRepository(json);

      assertThatThrownBy(() -> repository.get(path)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldGetEmptyHistoryFromUnknownFiles() {
      ProjectPath path = folder().build();

      ProjectHistory history = histories.get(path);

      assertThat(history.path()).isEqualTo(path);
      assertThat(history.actions()).isEmpty();
    }

    @Test
    void shouldGetExistingHistory() {
      ProjectPath path = folder().add("src/test/resources/projects/history/history.json", ".seed4j/modules/history.json").build();

      ProjectHistory history = histories.get(path);

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

      ProjectHistory history = histories.get(path);

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
          "src/test/resources/projects/composed-history/20250819203001002-another-test-module.json",
          ".seed4j/modules/20250819203001002-another-test-module.json"
        )
        .add("src/test/resources/projects/composed-history/dummy.txt", ".seed4j/modules/dummy.txt")
        .build();

      ProjectHistory history = histories.get(path);

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
