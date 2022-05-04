package tech.jhipster.lite.generator.client.vue.security.jwt.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.vue.security.jwt.domain.JWTDomainService;
import tech.jhipster.lite.generator.client.vue.security.jwt.domain.JWTService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Configuration
public class JWTBeanConfiguration {

  private final ProjectRepository projectRepository;

  public JWTBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public JWTService jwtService() {
    return new JWTDomainService(projectRepository);
  }
}
