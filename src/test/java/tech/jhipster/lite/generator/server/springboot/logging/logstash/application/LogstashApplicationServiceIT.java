package tech.jhipster.lite.generator.server.springboot.logging.logstash.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.logging.logstash.application.LogstashAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.logging.logstash.application.LogstashAssert.assertJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.logging.logstash.application.LogstashAssert.assertLoggerInConfiguration;
import static tech.jhipster.lite.generator.server.springboot.logging.logstash.application.LogstashAssert.assertProperties;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class LogstashApplicationServiceIT {

  @Autowired
  LogstashApplicationService logstashApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    logstashApplicationService.init(project);

    assertDependencies(project);
    assertJavaFiles(project);
    assertProperties(project);
    assertLoggerInConfiguration(project);
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    logstashApplicationService.addDependencies(project);

    assertDependencies(project);
  }

  @Test
  void shouldAddJavaFiles() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    logstashApplicationService.addJavaFiles(project);

    assertJavaFiles(project);
  }

  @Test
  void shouldAddProperties() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    logstashApplicationService.addProperties(project);

    assertProperties(project);
  }

  @Test
  void shouldAddLoggerConfiguration() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    logstashApplicationService.addLoggerInConfiguration(project);

    assertLoggerInConfiguration(project);
  }
}
