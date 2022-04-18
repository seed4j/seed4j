package tech.jhipster.lite.generator.client.vue.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.vue.core.domain.VueService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public record VueApplicationService(VueService vueService) {
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
