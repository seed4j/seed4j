package tech.jhipster.lite.generator.server.javatool.base.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.javatool.base.domain.JavaBaseService;

@Service
public record JavaBaseApplicationService(JavaBaseService javaBaseService) {
  public void addJavaBase(Project project) {
    javaBaseService.addJavaBase(project);
  }
}
