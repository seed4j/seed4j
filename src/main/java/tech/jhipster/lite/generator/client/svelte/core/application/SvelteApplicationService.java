package tech.jhipster.lite.generator.client.svelte.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.svelte.core.domain.SvelteService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public record SvelteApplicationService(SvelteService svelteService) {
  public void addSvelte(Project project) {
    svelteService.addSvelte(project);
  }

  public void addStyledSvelteKit(Project project) {
    svelteService.addStyledSvelteKit(project);
  }
}
