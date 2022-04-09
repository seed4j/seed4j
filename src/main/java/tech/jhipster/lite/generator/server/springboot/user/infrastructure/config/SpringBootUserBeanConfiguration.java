package tech.jhipster.lite.generator.server.springboot.user.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.user.domain.SpringBootUserDomainService;
import tech.jhipster.lite.generator.server.springboot.user.domain.SpringBootUserService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SpringBootUserBeanConfiguration {

  private final ProjectRepository projectRepository;

  public SpringBootUserBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public SpringBootUserService springBootUserService() {
    return new SpringBootUserDomainService(projectRepository);
  }
}
