package tech.jhipster.lite.generator.client.tools.cypress.application;

import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonComplete;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.react.core.application.ReactApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class CypressApplicationServiceIT {

  @Autowired
  CypressApplicationService cypressApplicationService;

  @Autowired
  ReactApplicationService reactApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPackageJsonComplete();
    reactApplicationService.addReact(project);

    cypressApplicationService.init(project);

    CypressAssert.assertDependency(project);
    CypressAssert.assertCypressScripts(project);
    CypressAssert.assertCypressFiles(project);
    CypressAssert.assertCypressFiles(project);
    CypressAssert.assertCypressTestFiles(project);
  }
}
