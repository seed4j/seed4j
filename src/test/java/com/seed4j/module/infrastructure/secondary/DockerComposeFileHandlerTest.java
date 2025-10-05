package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.contentNormalizingNewLines;
import static com.seed4j.TestFileUtils.loadDefaultProperties;
import static com.seed4j.TestFileUtils.tmpDirForTest;
import static com.seed4j.module.domain.Seed4JModule.dockerComposeFile;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class DockerComposeFileHandlerTest {

  public static final Path EXISTING_SPRING_COMPOSE = Path.of(
    "src/test/resources/projects/project-with-root-compose-file/docker-compose.yml"
  );
  public static final String COMPOSE_FILE_NAME = "docker-compose.yml";

  @ParameterizedTest
  @ValueSource(ints = { 2, 4 })
  void shouldCreateUnknownFileWithSpaces(int spaces) {
    Path rootComposeFile = Path.of(tmpDirForTest(), COMPOSE_FILE_NAME);
    DockerComposeFileHandler handler = new DockerComposeFileHandler(Indentation.from(spaces), rootComposeFile);

    handler.append(redisDockerComposeFileReference());

    assertThat(contentNormalizingNewLines(rootComposeFile)).isEqualTo(redisDockerComposeResult(spaces));
  }

  @Test
  void shouldAppendPropertyToDockerComposeFileWithExistingInclude() {
    Path rootComposeFile = Path.of(tmpDirForTest(), COMPOSE_FILE_NAME);
    loadDefaultProperties(EXISTING_SPRING_COMPOSE, rootComposeFile);
    DockerComposeFileHandler handler = new DockerComposeFileHandler(Indentation.DEFAULT, rootComposeFile);

    handler.append(kafkaDockerComposeFileReference());

    assertThat(contentNormalizingNewLines(rootComposeFile)).isEqualTo(
      """
      include:
        - src/main/docker/redis.yml
        - src/main/docker/kafka.yml
      """
    );
  }

  @Test
  void shouldNotAppendExistingDockerComposePath() {
    Path rootComposeFile = Path.of(tmpDirForTest(), COMPOSE_FILE_NAME);
    loadDefaultProperties(EXISTING_SPRING_COMPOSE, rootComposeFile);
    DockerComposeFileHandler handler = new DockerComposeFileHandler(Indentation.DEFAULT, rootComposeFile);

    handler.append(redisDockerComposeFileReference());

    assertThat(contentNormalizingNewLines(rootComposeFile)).isEqualTo(redisDockerComposeResult(2));
  }

  @Test
  void shouldNotAppendDuplicatedDockerComposePath() {
    Path rootComposeFile = Path.of(tmpDirForTest(), "docker-compose.yml");
    loadDefaultProperties(EXISTING_SPRING_COMPOSE, rootComposeFile);
    DockerComposeFileHandler handler = new DockerComposeFileHandler(Indentation.DEFAULT, rootComposeFile);

    handler.append(redisDockerComposeFileReference());
    handler.append(redisDockerComposeFileReference());

    assertThat(contentNormalizingNewLines(rootComposeFile)).isEqualTo(redisDockerComposeResult(2));
  }

  private DockerComposeFile redisDockerComposeFileReference() {
    return dockerComposeFile("src/main/docker/redis.yml");
  }

  private String redisDockerComposeResult(int spaces) {
    StringBuilder sb = new StringBuilder();

    String indentation = " ".repeat(spaces);
    return sb.append("include:\n").append(indentation).append("- src/main/docker/redis.yml\n").toString();
  }

  private DockerComposeFile kafkaDockerComposeFileReference() {
    return dockerComposeFile("src/main/docker/kafka.yml");
  }
}
