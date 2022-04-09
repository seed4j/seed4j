package tech.jhipster.lite.generator.client.vue.core.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.vue.core.domain.VueDomainService;
import tech.jhipster.lite.generator.client.vue.core.domain.VueService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class VueBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public VueBeanConfiguration(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Bean
  public VueService vueService() {
    return new VueDomainService(projectRepository, npmService);
  }
}
