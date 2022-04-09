package tech.jhipster.lite.generator.server.springboot.mvc.springdoc.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class SpringdocApplicationService {

  private final SpringdocService springdocService;

  public SpringdocApplicationService(SpringdocService springdocService) {
    this.springdocService = springdocService;
  }

  public void init(Project project) {
    springdocService.init(project);
  }

  public void initWithSecurityJWT(Project project) {
    springdocService.initWithSecurityJWT(project);
  }
}
