package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.infrastructure.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain.LiquibaseDomainService;
import tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain.LiquibaseService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class LiquibaseBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final Clock clock;

  public LiquibaseBeanConfiguration(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    Clock clock
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.clock = clock;
  }

  @Bean
  public LiquibaseService liquibaseService() {
    return new LiquibaseDomainService(projectRepository, buildToolService, springBootCommonService, clock);
  }
}
