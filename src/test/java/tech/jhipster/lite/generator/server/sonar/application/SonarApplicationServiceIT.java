package tech.jhipster.lite.generator.server.sonar.application;

import static tech.jhipster.lite.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class SonarApplicationServiceIT {

  @Autowired
  SonarApplicationService sonarApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Test
  void shouldAddSonarJavaBackend() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    sonarApplicationService.addSonarJavaBackend(project);

    SonarAssert.assertFiles(project);
    SonarAssert.assertPomXml(project);
  }

  @Test
  void shouldAddSonarJavaBackendAndFrontend() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    sonarApplicationService.addSonarJavaBackendAndFrontend(project);

    SonarAssert.assertFiles(project);
    SonarAssert.assertFrontProperties(project);
    SonarAssert.assertPomXml(project);
  }
}
