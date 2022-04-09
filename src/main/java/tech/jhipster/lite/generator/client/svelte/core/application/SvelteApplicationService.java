package tech.jhipster.lite.generator.client.svelte.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.svelte.core.domain.SvelteService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class SvelteApplicationService {

  private final SvelteService svelteService;

  public SvelteApplicationService(SvelteService svelteService) {
    this.svelteService = svelteService;
  }

  public void addSvelte(Project project) {
    svelteService.addSvelte(project);
  }

  public void addStyledSvelteKit(Project project) {
    svelteService.addStyledSvelteKit(project);
  }
}
