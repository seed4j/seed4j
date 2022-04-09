package tech.jhipster.lite.generator.server.javatool.arch.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.javatool.arch.domain.JavaArchUnitDomainService;
import tech.jhipster.lite.generator.server.javatool.arch.domain.JavaArchUnitService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class JavaArchUnitBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;

  public JavaArchUnitBeanConfiguration(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
  }

  @Bean
  public JavaArchUnitService javaArchUnitService() {
    return new JavaArchUnitDomainService(projectRepository, buildToolService, springBootCommonService);
  }
}
