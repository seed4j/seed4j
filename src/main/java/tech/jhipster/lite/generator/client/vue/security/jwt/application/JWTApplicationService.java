package tech.jhipster.lite.generator.client.vue.security.jwt.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.vue.security.jwt.domain.JWTService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public class JWTApplicationService {

  private final JWTService jwtService;

  public JWTApplicationService(JWTService jwtService) {
    this.jwtService = jwtService;
  }

  public void addJWT(Project project) {
    jwtService.addJWT(project);
  }
}
