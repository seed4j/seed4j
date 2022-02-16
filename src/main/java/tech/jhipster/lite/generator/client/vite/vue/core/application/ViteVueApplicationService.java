package tech.jhipster.lite.generator.client.vite.vue.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.vite.vue.core.domain.ViteVueService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public class ViteVueApplicationService {

  private final ViteVueService viteVueService;

  public ViteVueApplicationService(ViteVueService viteVueService) {
    this.viteVueService = viteVueService;
  }

  public void addViteVue(Project project) {
    viteVueService.addViteVue(project);
  }

  public void addStyledViteVue(Project project) {
    viteVueService.addStyledViteVue(project);
  }
}
