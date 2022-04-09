package tech.jhipster.lite.generator.server.springboot.database.sqlcommon.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonDomainService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SQLCommonBeanConfiguration {

  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final ProjectRepository projectRepository;

  public SQLCommonBeanConfiguration(
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    ProjectRepository projectRepository
  ) {
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.projectRepository = projectRepository;
  }

  @Bean
  public SQLCommonService sqlCommonService() {
    return new SQLCommonDomainService(buildToolService, springBootCommonService, projectRepository);
  }
}
