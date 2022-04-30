package tech.jhipster.lite.generator.client.angular.core.application;

import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonComplete;
import static tech.jhipster.lite.generator.client.angular.core.application.AngularAssert.assertAngularFiles;
import static tech.jhipster.lite.generator.client.angular.core.application.AngularAssert.assertAppJwt;
import static tech.jhipster.lite.generator.client.angular.core.application.AngularAssert.assertConfigFiles;
import static tech.jhipster.lite.generator.client.angular.core.application.AngularAssert.assertDependencies;
import static tech.jhipster.lite.generator.client.angular.core.application.AngularAssert.assertDevDependencies;
import static tech.jhipster.lite.generator.client.angular.core.application.AngularAssert.assertScripts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class AngularApplicationServiceIT {

  @Autowired
  AngularApplicationService angularApplicationService;

  @Test
  void shouldAddAngular() {
    Project project = tmpProjectWithPackageJsonComplete();

    angularApplicationService.addAngular(project);

    assertDevDependencies(project);
    assertDependencies(project);
    assertScripts(project);
    assertConfigFiles(project);
    assertAngularFiles(project);
  }

  @Test
  void shouldAddJwtAngular() {
    Project project = tmpProjectWithPackageJsonComplete();

    angularApplicationService.addAngular(project);
    angularApplicationService.addJwtAngular(project);

    assertDevDependencies(project);
    assertDependencies(project);
    assertScripts(project);
    assertConfigFiles(project);
    assertAngularFiles(project);
    assertAppJwt(project);
  }
}
