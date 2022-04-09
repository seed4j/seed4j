package tech.jhipster.lite.generator.server.javatool.arch.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitAssertFiles.assertArchUnitMavenPlugin;
import static tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitAssertFiles.assertArchunitProperties;
import static tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitAssertFiles.assertFilesAnnotations;
import static tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitAssertFiles.assertFilesHexaArchTest;
import static tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitAssertFiles.assertLoggerInConfiguration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class JavaArchUnitApplicationServiceIT {

  @Autowired
  JavaArchUnitApplicationService javaArchUnitApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    javaArchUnitApplicationService.init(project);

    assertArchUnitMavenPlugin(project);
    assertFilesAnnotations(project);
    assertFilesHexaArchTest(project);
    assertArchunitProperties(project);
    assertLoggerInConfiguration(project);
  }
}
