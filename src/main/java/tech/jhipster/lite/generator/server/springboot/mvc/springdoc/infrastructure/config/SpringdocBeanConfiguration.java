package tech.jhipster.lite.generator.server.springboot.mvc.springdoc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocDomainService;
import tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SpringdocBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootCommonService springBootCommonService;

  public SpringdocBeanConfiguration(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootCommonService springBootCommonService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
  }

  @Bean
  public SpringdocService springdocService() {
    return new SpringdocDomainService(buildToolService, projectRepository, springBootCommonService);
  }
}
