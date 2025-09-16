package com.seed4j.module.domain.standalonedocker;

import static com.seed4j.module.domain.Seed4JModule.dockerComposeFile;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModulesFixture.allProperties;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JModuleDockerComposeFileTest {

  @Test
  void hasSimpleString() {
    Seed4JModuleDockerComposeFile dockerComposeFile = Seed4JModuleDockerComposeFile.builder(moduleBuilder(allProperties()))
      .append(dockerComposeFile("src/main/docker/redis.yml"))
      .append(dockerComposeFile("src/main/docker/kafka.yml"))
      .build();

    assertThat(dockerComposeFile).hasToString(
      "DockerComposeFiles[files=[DockerComposeFile[path=src/main/docker/redis.yml], DockerComposeFile[path=src/main/docker/kafka.yml]]]"
    );
  }
}
