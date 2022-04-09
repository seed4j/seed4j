package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.infrastructure.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain.FlywayDomainService;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain.FlywayService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class FlywayBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootCommonService springBootCommonService;
  private final Clock clock;

  public FlywayBeanConfiguration(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootCommonService springBootCommonService,
    Clock clock
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
    this.clock = clock;
  }

  @Bean
  public FlywayService flywayService() {
    return new FlywayDomainService(buildToolService, projectRepository, springBootCommonService, clock);
  }
}
