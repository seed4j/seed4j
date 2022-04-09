package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application.EurekaAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application.EurekaAssert.assertDockerCompose;
import static tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application.EurekaAssert.assertProperties;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class EurekaApplicationServiceIT {

  @Autowired
  EurekaApplicationService eurekaApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    // Given
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "Foo");

    projectApplicationService.init(project);

    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    // When
    eurekaApplicationService.init(project);

    // Then
    assertDependencies(project);
    assertProperties(project);
    assertDockerCompose(project);
  }
}
