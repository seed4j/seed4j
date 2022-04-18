package tech.jhipster.lite.generator.server.javatool.arch.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.javatool.arch.domain.JavaArchUnitService;

@Service
public record JavaArchUnitApplicationService(JavaArchUnitService javaArchUnitService) {
  public void init(Project project) {
    javaArchUnitService.init(project);
  }
}
