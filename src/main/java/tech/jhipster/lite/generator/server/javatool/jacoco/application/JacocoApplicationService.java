package tech.jhipster.lite.generator.server.javatool.jacoco.application;

import org.springframework.stereotype.Component;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.javatool.jacoco.domain.JacocoService;

@Component
public record JacocoApplicationService(JacocoService jacocoService) {
  public void addCheckMinimumCoverage(Project project) {
    jacocoService.addCheckMinimumCoverage(project);
  }
}
