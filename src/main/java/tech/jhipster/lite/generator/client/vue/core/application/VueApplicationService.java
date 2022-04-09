package tech.jhipster.lite.generator.client.vue.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.vue.core.domain.VueService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class VueApplicationService {

  private final VueService vueService;

  public VueApplicationService(VueService vueService) {
    this.vueService = vueService;
  }

  public void addVue(Project project) {
    vueService.addVue(project);
  }

  public void addPinia(Project project) {
    vueService.addPinia(project);
  }

  public void addStyledVue(Project project) {
    vueService.addStyledVue(project);
  }
}
