package tech.jhipster.lite.generator.client.react.core.application;

import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class ReactApplicationServiceIT {

  @Autowired
  ReactApplicationService reactApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPackageJsonComplete();

    reactApplicationService.init(project);

    ReactAssert.assertDependencies(project);
    ReactAssert.assertScripts(project);
    ReactAssert.assertConfigFiles(project);
    ReactAssert.assertReactFiles(project);
  }
}
