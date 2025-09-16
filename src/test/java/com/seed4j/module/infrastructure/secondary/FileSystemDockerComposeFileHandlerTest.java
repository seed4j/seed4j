package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.contentNormalizingNewLines;
import static com.seed4j.TestFileUtils.loadDefaultProperties;
import static com.seed4j.TestFileUtils.tmpDirForTest;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModulesFixture.allProperties;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.domain.standalonedocker.Seed4JModuleDockerComposeFile;
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

    handler.handle(new Seed4JProjectFolder(folder), dockerComposeFile(redisDockerComposeFileReference()));

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

    handler.handle(new Seed4JProjectFolder(folder), dockerComposeFile(kafkaDockerComposeFileReference()));

    assertThat(contentNormalizingNewLines(propertiesFile)).contains(
      """
      include:
        - src/main/docker/redis.yml
        - src/main/docker/kafka.yml
      """
    );
  }

  private Seed4JModuleDockerComposeFile dockerComposeFile(DockerComposeFile file) {
    return Seed4JModuleDockerComposeFile.builder(moduleBuilder(allProperties())).append(file).build();
  }

  private DockerComposeFile redisDockerComposeFileReference() {
    return Seed4JModule.dockerComposeFile("src/main/docker/redis.yml");
  }

  private DockerComposeFile kafkaDockerComposeFileReference() {
    return Seed4JModule.dockerComposeFile("src/main/docker/kafka.yml");
  }
}
