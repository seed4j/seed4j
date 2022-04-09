package tech.jhipster.lite.generator.server.springboot.springcloud.consul.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.springcloud.consul.application.ConsulAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.springcloud.consul.application.ConsulAssert.assertDockerConsul;
import static tech.jhipster.lite.generator.server.springboot.springcloud.consul.application.ConsulAssert.assertProperties;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class ConsulApplicationServiceIT {

  @Autowired
  ConsulApplicationService consulApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "Foo");

    projectApplicationService.init(project);

    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    consulApplicationService.init(project);

    assertDependencies(project);
    assertProperties(project);
    assertDockerConsul(project);
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    consulApplicationService.addDependencies(project);

    assertDependencies(project);
  }

  @Test
  void shouldAddProperties() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    consulApplicationService.addProperties(project);

    assertProperties(project);
  }

  @Test
  void shouldAddDockerConsul() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    consulApplicationService.addDockerConsul(project);

    assertDockerConsul(project);
  }
}
