package tech.jhipster.lite.generator.server.springboot.logging.logstash.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.logging.logstash.domain.LogstashDomainService;
import tech.jhipster.lite.generator.server.springboot.logging.logstash.domain.LogstashService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class LogstashBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootCommonService springBootCommonService;

  public LogstashBeanConfiguration(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootCommonService springBootCommonService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
  }

  @Bean
  public LogstashService logstashService() {
    return new LogstashDomainService(buildToolService, projectRepository, springBootCommonService);
  }
}
