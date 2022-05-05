package tech.jhipster.lite.generator.client.vue.security.jwt.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.vue.security.jwt.domain.VueJwtService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public class VueJwtApplicationService {

  private final VueJwtService vueJwtService;

  public VueJwtApplicationService(VueJwtService jwtService) {
    this.vueJwtService = jwtService;
  }

  public void addJWT(Project project) {
    vueJwtService.addJWT(project);
  }
}
