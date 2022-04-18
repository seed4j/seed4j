package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain.JwtSecurityService;

@Service
public record JwtSecurityApplicationService(JwtSecurityService jwtSecurityService) {
  public void init(Project project) {
    jwtSecurityService.init(project);
  }

  public void addBasicAuth(Project project) {
    jwtSecurityService.addBasicAuth(project);
  }
}
