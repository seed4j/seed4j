package tech.jhipster.lite.generator.client.common.application;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class ClientCommonApplicationServiceIT {

  @Autowired
  ClientCommonApplicationService clientCommonApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Test
  void shouldExcludeInTsconfigJson() throws IOException {
    // Given
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");
    projectApplicationService.init(project);

    String fileName = "tsconfig.json";
    Path existingTsconfigJsonFilePath = Path.of(getPath(project.getFolder(), ".", fileName));
    String existingTsconfigFileContent = """
      {
        "include": ["value1"],
        "exclude": ["value1"]
      }
      """;
    Files.write(existingTsconfigJsonFilePath, existingTsconfigFileContent.getBytes());

    // When
    clientCommonApplicationService.excludeInTsconfigJson(project, "value2");

    // Then
    List<String> expectedContentLines =
      """
      {
        "include": ["value1"],
        "exclude": ["value1", "value2"]
      }
      """.lines().toList();
    assertThat(Files.readAllLines(existingTsconfigJsonFilePath)).containsExactlyElementsOf(expectedContentLines);
  }
}
