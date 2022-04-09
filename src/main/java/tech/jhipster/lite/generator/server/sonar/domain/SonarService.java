package tech.jhipster.lite.generator.server.sonar.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface SonarService {
  void addSonarJavaBackend(Project project);
  void addSonarJavaBackendAndFrontend(Project project);
}
