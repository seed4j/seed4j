package tech.jhipster.lite.generator.server.springboot.mvc.security.common.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.security.common.domain.CommonSecurityDomainService;
import tech.jhipster.lite.generator.server.springboot.mvc.security.common.domain.CommonSecurityService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class CommonSecurityBeanConfiguration {

  private final ProjectRepository projectRepository;

  public CommonSecurityBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public CommonSecurityService commonSecurityService() {
    return new CommonSecurityDomainService(projectRepository);
  }
}
