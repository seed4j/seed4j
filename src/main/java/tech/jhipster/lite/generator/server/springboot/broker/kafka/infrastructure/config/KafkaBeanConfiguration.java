package tech.jhipster.lite.generator.server.springboot.broker.kafka.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.KafkaDomainService;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.KafkaService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class KafkaBeanConfiguration {

  private final BuildToolService buildToolService;

  private final ProjectRepository projectRepository;

  private final SpringBootCommonService springBootCommonService;

  private final DockerService dockerService;

  public KafkaBeanConfiguration(
    final BuildToolService buildToolService,
    final ProjectRepository projectRepository,
    final SpringBootCommonService springBootCommonService,
    final DockerService dockerService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
    this.dockerService = dockerService;
  }

  @Bean
  public KafkaService kafkaService() {
    return new KafkaDomainService(buildToolService, projectRepository, springBootCommonService, dockerService);
  }
}
