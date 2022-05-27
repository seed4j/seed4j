package tech.jhipster.lite.generator.project.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.ProjectDomainService;
import tech.jhipster.lite.generator.project.domain.ProjectService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@Configuration
public class ProjectBeanConfiguration {

  private final NpmService npmService;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;

  public ProjectBeanConfiguration(
    NpmService npmService,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService
  ) {
    this.npmService = npmService;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
  }

  @Bean
  public ProjectService projectService() {
    return new ProjectDomainService(npmService, buildToolService, springBootCommonService);
  }
}
