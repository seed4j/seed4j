package tech.jhipster.lite.generator.server.springboot.database.postgresql.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.postgresql.domain.PostgresqlDomainService;
import tech.jhipster.lite.generator.server.springboot.database.postgresql.domain.PostgresqlService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class PostgresqlBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final SQLCommonService sqlCommonService;
  private final DockerService dockerService;

  public PostgresqlBeanConfiguration(
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
  public PostgresqlService postgresqlService() {
    return new PostgresqlDomainService(projectRepository, buildToolService, springBootCommonService, sqlCommonService, dockerService);
  }
}
