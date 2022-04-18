package tech.jhipster.lite.generator.server.springboot.mvc.springdoc.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocService;

@Service
public record SpringdocApplicationService(SpringdocService springdocService) {
  public void init(Project project) {
    springdocService.init(project);
  }

  public void initWithSecurityJWT(Project project) {
    springdocService.initWithSecurityJWT(project);
  }
}
