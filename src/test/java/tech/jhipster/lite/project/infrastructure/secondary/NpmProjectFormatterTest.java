package tech.jhipster.lite.project.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.project.domain.ProjectPath;

@UnitTest
@Disabled("Only works on computer with npm installed and usable")
class NpmProjectFormatterTest {

  private static final NpmProjectFormatter formatter = new NpmProjectFormatter("npm");

  @Test
  void shouldFormatProject() throws IOException {
    String directory = TestFileUtils.tmpDirForTest();
    Path jsonFile = simpleNpmProject(directory);

    formatter.format(new ProjectPath(directory));

    assertThat(Files.readString(jsonFile))
      .isEqualTo(
        """
        { "key": "value" }
        """
      );
  }

  private static Path simpleNpmProject(String directory) throws IOException {
    Path path = Paths.get(directory);
    Files.createDirectories(path);

    Files.copy(Paths.get("src/test/resources/projects/files/package.json"), path.resolve("package.json"));

    Path jsonFile = path.resolve("file.json");
    Files.write(
      jsonFile,
      """
      {"key":"value"}
      """.getBytes(StandardCharsets.UTF_8)
    );

    return jsonFile;
  }
}
