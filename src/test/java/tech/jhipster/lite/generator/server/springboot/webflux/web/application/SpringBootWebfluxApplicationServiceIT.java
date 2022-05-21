package tech.jhipster.lite.generator.server.springboot.webflux.web.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertActuatorDependencies;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertActuatorProperties;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertExceptionHandlerDependencies;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertExceptionHandlerFiles;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertExceptionHandlerProperties;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertProperties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
class SpringBootWebfluxApplicationServiceIT {

  @Autowired
  SpringBootWebfluxApplicationService springBootWebfluxApplicationService;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebfluxApplicationService.init(project);

    assertDependencies(project);
    assertProperties(project);
    assertExceptionHandlerDependencies(project);
    assertExceptionHandlerProperties(project);
    assertExceptionHandlerFiles(project);
    assertActuatorDependencies(project);
    assertActuatorProperties(project);
  }

  @Test
  void shouldAddSpringBootWebflux() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebfluxApplicationService.addSpringBootWebflux(project);

    assertDependencies(project);
    assertProperties(project);
  }

  @Test
  void shouldAddExceptionHandler() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebfluxApplicationService.addExceptionHandler(project);

    assertExceptionHandlerDependencies(project);
    assertExceptionHandlerProperties(project);
    assertExceptionHandlerFiles(project);
  }
}
