package tech.jhipster.lite.generator.server.springboot.mvc.web.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvcDomainService;
import tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvcService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SpringBootMvcBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;

  public SpringBootMvcBeanConfiguration(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
  }

  @Bean
  public SpringBootMvcService springBootMvcService() {
    return new SpringBootMvcDomainService(projectRepository, buildToolService, springBootCommonService);
  }
}
