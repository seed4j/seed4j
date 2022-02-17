package tech.jhipster.lite.generator.client.angular.core.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.client.angular.core.application.AngularAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class AngularApplicationServiceIT {

  @Autowired
  AngularApplicationService angularApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPackageJsonComplete();

    angularApplicationService.init(project);

    assertDevDependencies(project);
    assertDependencies(project);
    assertScripts(project);
    assertConfigFiles(project);
    assertAngularFiles(project);
  }
}
