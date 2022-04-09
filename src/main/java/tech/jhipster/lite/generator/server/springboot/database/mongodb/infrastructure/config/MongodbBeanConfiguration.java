package tech.jhipster.lite.generator.server.springboot.database.mongodb.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.MongodbDomainService;
import tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.MongodbService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class MongodbBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final SQLCommonService sqlCommonService;
  private final DockerService dockerService;

  public MongodbBeanConfiguration(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    SQLCommonService sqlCommonService,
    DockerService dockerService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.sqlCommonService = sqlCommonService;
    this.dockerService = dockerService;
  }

  @Bean
  public MongodbService mongodbService() {
    return new MongodbDomainService(projectRepository, buildToolService, springBootCommonService, sqlCommonService, dockerService);
  }
}
