package tech.jhipster.lite.generator.client.angular.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.angular.core.domain.AngularService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public record AngularApplicationService(AngularService angularService) {
  public void addAngular(Project project) {
    angularService.addAngular(project);
  }

  public void addStyledAngular(Project project) {
    angularService.addStyledAngular(project);
  }

  public void addJwtAngular(Project project) {
    angularService.addJwtAngular(project);
  }
}
