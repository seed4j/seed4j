package com.seed4j.project.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.download.Project;
import com.seed4j.project.domain.download.ProjectName;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class FileSystemProjectsRepositoryTest {

  private static final FileSystemProjectsRepository projects = new FileSystemProjectsRepository();

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
