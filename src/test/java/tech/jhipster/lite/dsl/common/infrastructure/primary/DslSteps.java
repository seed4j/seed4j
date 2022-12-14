package tech.jhipster.lite.dsl.common.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.TestProjects.lastProjectFolder;
import static tech.jhipster.lite.TestProjects.newTestFolder;

import io.cucumber.java.en.When;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import tech.jhipster.lite.module.infrastructure.secondary.git.GitTestUtil;

public class DslSteps {

  @Autowired
  private TestRestTemplate rest;

  @When("I apply dsl to a directory")
  public void applyDslToADirectory() {
    String projectFolder = newTestFolder();

    //    addPackageJsonToProject(projectFolder);
    addPomToProject(projectFolder);

    post("/api/apply-dsl", projectFolder);
  }

  private void post(String uri, String projectFolder) {
    var multipart = new LinkedMultiValueMap<>();
    multipart.add("file", file());
    multipart.add(
      "properties",
      """
            {
            	"projectFolder":"{{FOLDER}}",
            	"commit":false
            }
            """.replace(
          "{{FOLDER}}",
          projectFolder
        )
    );
    rest.postForEntity(uri, new HttpEntity<>(multipart, multipartHeaders()), Void.class);
  }

  private FileSystemResource file() {
    return new FileSystemResource(Path.of("src", "test", "resources", "dsl", "jhlite-test.dsl"));
  }

  private HttpHeaders multipartHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    return headers;
  }

  private static void addPackageJsonToProject(String folder) {
    addFileToProject(folder, "src/test/resources/projects/empty-node/package.json", "package.json");
  }

  private static void addPomToProject(String folder) {
    addFileToProject(folder, "src/test/resources/projects/init-maven/pom.xml", "pom.xml");
  }

  private static void addFileToProject(String folder, String source, String destination) {
    Path folderPath = Paths.get(folder);
    try {
      Files.createDirectories(folderPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    Path filePath = folderPath.resolve(destination);
    try {
      Files.copy(Paths.get(source), filePath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
