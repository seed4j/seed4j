package tech.jhipster.lite.generator.client.react.core.application;

import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonComplete;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class ReactApplicationServiceIT {

  @Autowired
  ReactApplicationService reactApplicationService;

  @Test
  void shouldAddReact() {
    Project project = tmpProjectWithPackageJsonComplete();

    reactApplicationService.addReact(project);

    ReactAssert.assertDependency(project);
    ReactAssert.assertScripts(project);
    ReactAssert.assertConfig(project);
    ReactAssert.assertFiles(project);
    ReactAssert.assertReactFiles(project);
    ReactAssert.assertJestSonar(project);
  }

  @Test
  void shouldAddStyledReact() {
    Project project = tmpProjectWithPackageJsonComplete();

    reactApplicationService.addStyledReact(project);
    ReactAssert.assertDependency(project);
    ReactAssert.assertScripts(project);
    ReactAssert.assertConfig(project);
    ReactAssert.assertFiles(project);
    ReactAssert.assertReactFiles(project);
    ReactAssert.assertJestSonar(project);
  }
}
