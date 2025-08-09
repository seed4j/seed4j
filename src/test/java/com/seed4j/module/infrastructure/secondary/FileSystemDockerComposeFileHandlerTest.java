package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.contentNormalizingNewLines;
import static com.seed4j.TestFileUtils.loadDefaultProperties;
import static com.seed4j.TestFileUtils.tmpDirForTest;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModulesFixture.allProperties;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.module.domain.standalonedocker.JHipsterModuleDockerComposeFile;
import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@UnitTest
public class FileSystemDockerComposeFileHandlerTest {

  public static final Path EXISTING_SPRING_COMPOSE = Path.of(
    "src/test/resources/projects/project-with-root-compose-file/docker-compose.yml"
  );
  public static final String COMPOSE_FILE_NAME = "docker-compose.yml";

  private static final FileSystemDockerComposeFileHandler handler = new FileSystemDockerComposeFileHandler();

  @Test
  void shouldCreateDefaultRootDockerComposeFileForProjectWithoutIntegration() {
    String folder = tmpDirForTest();

    handler.handle(new SeedProjectFolder(folder), dockerComposeFile(redisDockerComposeFileReference()));

    assertThat(contentNormalizingNewLines(Path.of(folder, COMPOSE_FILE_NAME))).contains(
        """
        include:
          - src/main/docker/redis.yml
        """
      );
  }

  @Test
  void shouldUpdateRootDockerComposeFile() {
    String folder = tmpDirForTest();
    Path propertiesFile = Path.of(folder, COMPOSE_FILE_NAME);
    loadDefaultProperties(EXISTING_SPRING_COMPOSE, propertiesFile);

    handler.handle(new SeedProjectFolder(folder), dockerComposeFile(kafkaDockerComposeFileReference()));

    assertThat(contentNormalizingNewLines(propertiesFile)).contains(
        """
        include:
          - src/main/docker/redis.yml
          - src/main/docker/kafka.yml
        """
      );
  }

  private JHipsterModuleDockerComposeFile dockerComposeFile(DockerComposeFile file) {
    return JHipsterModuleDockerComposeFile.builder(moduleBuilder(allProperties())).append(file).build();
  }

  private DockerComposeFile redisDockerComposeFileReference() {
    return JHipsterModule.dockerComposeFile("src/main/docker/redis.yml");
  }

  private DockerComposeFile kafkaDockerComposeFileReference() {
    return JHipsterModule.dockerComposeFile("src/main/docker/kafka.yml");
  }
}
