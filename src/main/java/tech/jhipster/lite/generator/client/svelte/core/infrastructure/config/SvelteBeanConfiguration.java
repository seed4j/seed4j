package tech.jhipster.lite.generator.client.svelte.core.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.svelte.core.domain.SvelteDomainService;
import tech.jhipster.lite.generator.client.svelte.core.domain.SvelteService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SvelteBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public SvelteBeanConfiguration(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Bean
  public SvelteService svelteService() {
    return new SvelteDomainService(projectRepository, npmService);
  }
}
