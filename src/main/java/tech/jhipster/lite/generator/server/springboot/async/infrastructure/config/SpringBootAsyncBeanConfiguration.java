package tech.jhipster.lite.generator.server.springboot.async.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.async.domain.SpringBootAsyncDomainService;
import tech.jhipster.lite.generator.server.springboot.async.domain.SpringBootAsyncService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SpringBootAsyncBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final SpringBootCommonService springBootCommonService;

  public SpringBootAsyncBeanConfiguration(ProjectRepository projectRepository, SpringBootCommonService springBootCommonService) {
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
  }

  @Bean
  public SpringBootAsyncService springBootAsyncService() {
    return new SpringBootAsyncDomainService(projectRepository, springBootCommonService);
  }
}
