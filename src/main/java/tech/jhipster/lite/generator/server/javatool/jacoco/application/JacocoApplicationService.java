package tech.jhipster.lite.generator.server.javatool.jacoco.application;

import org.springframework.stereotype.Component;
import tech.jhipster.lite.generator.server.javatool.jacoco.domain.JacocoService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Component
public class JacocoApplicationService {

  private final JacocoService jacocoService;

  public JacocoApplicationService(JacocoService jacocoService) {
    this.jacocoService = jacocoService;
  }

  public void addCheckMinimumCoverage(Project project) {
    jacocoService.addCheckMinimumCoverage(project);
  }
}
