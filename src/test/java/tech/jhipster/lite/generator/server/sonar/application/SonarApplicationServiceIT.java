package tech.jhipster.lite.generator.server.sonar.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.POM_XML;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class SonarApplicationServiceIT {

  @Autowired
  SonarApplicationService sonarApplicationService;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Test
  void shouldAddSonarJavaBackend() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    sonarApplicationService.addSonarJavaBackend(project);

    SonarAssert.assertFiles(project);
    SonarAssert.assertPomXml(project);
  }

  @Test
  void shouldAddSonarJavaBackendAndFrontend() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    sonarApplicationService.addSonarJavaBackendAndFrontend(project);

    SonarAssert.assertFiles(project);
    SonarAssert.assertFrontProperties(project);
    SonarAssert.assertPomXml(project);
  }
}
