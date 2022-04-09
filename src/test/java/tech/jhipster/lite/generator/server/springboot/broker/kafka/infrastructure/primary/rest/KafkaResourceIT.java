package tech.jhipster.lite.generator.server.springboot.broker.kafka.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.Akhq.AKHQ_DOCKER_COMPOSE_FILE;
import static tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.Kafka.KAFKA_DOCKER_COMPOSE_FILE;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_DOCKER;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.application.KafkaApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class KafkaResourceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  KafkaApplicationService kafkaApplicationService;

  @Autowired
  DockerService dockerService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldInitKafka() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/brokers/kafka")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, MAIN_DOCKER + "/" + KAFKA_DOCKER_COMPOSE_FILE);
  }

  @Test
  void shouldAddProducer() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    kafkaApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/brokers/kafka/dummy-producer")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/kafka/KafkaProducerProperties.java");
    assertFileExist(
      projectPath,
      "src/test/java/tech/jhipster/chips/technical/infrastructure/secondary/kafka/KafkaProducerPropertiesTest.java"
    );
    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/dummy/infrastructure/secondary/kafka/producer/DummyProducer.java");
    assertFileExist(projectPath, "src/test/java/tech/jhipster/chips/dummy/infrastructure/secondary/kafka/producer/DummyProducerTest.java");
    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/kafka/KafkaConfiguration.java");
  }

  @Test
  void shouldAkhq() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    kafkaApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/brokers/kafka/akhq")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, MAIN_DOCKER + "/" + AKHQ_DOCKER_COMPOSE_FILE);
  }
}
