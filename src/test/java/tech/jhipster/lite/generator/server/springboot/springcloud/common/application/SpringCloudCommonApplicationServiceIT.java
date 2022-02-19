package tech.jhipster.lite.generator.server.springboot.springcloud.common.application;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
class SpringCloudCommonApplicationServiceIT {

  @Autowired
  SpringCloudCommonApplicationService springCloudCommonApplicationService;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldAddBootstrapProperties() throws IOException {
    // Given
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    String destinationFolderPath = "src/main/resources/config";
    String fileName = "bootstrap.properties";
    Path existingBootstrapFilePath = Path.of(getPath(project.getFolder(), destinationFolderPath, fileName));
    String existingBootstrapFileContent = """
      # bootstrap properties
      eureka.client.enabled=true
      """;
    Files.write(existingBootstrapFilePath, existingBootstrapFileContent.getBytes());

    // When
    String sourceFolderPath = "server/springboot/springcloud/eureka/src/test";
    springCloudCommonApplicationService.addOrMergeBootstrapProperties(project, sourceFolderPath, fileName, destinationFolderPath);

    // Then
    List<String> expectedContentLines =
      """
      # bootstrap properties
      eureka.client.enabled=true

      spring.application.name=foo
      """.lines().toList();
    assertThat(Files.readAllLines(existingBootstrapFilePath)).containsExactlyElementsOf(expectedContentLines);
  }
}
