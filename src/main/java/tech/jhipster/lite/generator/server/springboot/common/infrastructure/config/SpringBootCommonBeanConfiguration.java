package tech.jhipster.lite.generator.server.springboot.common.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonDomainService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SpringBootCommonBeanConfiguration {

  private final ProjectRepository projectRepository;

  public SpringBootCommonBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public SpringBootCommonService springBootCommonService() {
    return new SpringBootCommonDomainService(projectRepository);
  }
}
