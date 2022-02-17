package tech.jhipster.lite.generator.server.sonar.application;

import org.springframework.stereotype.Component;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.sonar.domain.SonarService;

@Component
public class SonarApplicationService {

  private final SonarService sonarService;

  public SonarApplicationService(SonarService sonarService) {
    this.sonarService = sonarService;
  }

  public void addSonarJavaBackend(Project project) {
    this.sonarService.addSonarJavaBackend(project);
  }

  public void addSonarJavaBackendAndFrontend(Project project) {
    this.sonarService.addSonarJavaBackendAndFrontend(project);
  }
}
