package tech.jhipster.lite.generator.client.vue.security.jwt.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.vue.security.jwt.domain.VueJwtDomainService;
import tech.jhipster.lite.generator.client.vue.security.jwt.domain.VueJwtService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Configuration
public class VueJwtBeanConfiguration {

  private final ProjectRepository projectRepository;

  public VueJwtBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public VueJwtService vueJwtService() {
    return new VueJwtDomainService(projectRepository);
  }
}
