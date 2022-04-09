package tech.jhipster.lite.generator.server.springboot.async.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.async.application.SpringBootAsyncAssert.assertJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.async.application.SpringBootAsyncAssert.assertProperties;
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
class SpringBootAsyncApplicationServiceIT {

  @Autowired
  SpringBootAsyncApplicationService springBootAsyncApplicationService;

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

    springBootAsyncApplicationService.init(project);

    assertJavaFiles(project);

    assertProperties(project);
  }

  @Test
  void shouldAddJavaFiles() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootAsyncApplicationService.addJavaFiles(project);

    assertJavaFiles(project);
  }

  @Test
  void shouldAddProperties() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootAsyncApplicationService.addProperties(project);

    assertProperties(project);
  }
}
