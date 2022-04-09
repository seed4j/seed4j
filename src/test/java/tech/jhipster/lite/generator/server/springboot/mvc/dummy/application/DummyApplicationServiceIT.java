package tech.jhipster.lite.generator.server.springboot.mvc.dummy.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.mvc.dummy.application.DummyAssert.assertJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.dummy.application.DummyAssert.assertTestFiles;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class DummyApplicationServiceIT {

  @Autowired
  DummyApplicationService dummyApplicationService;

  @Test
  void shouldApplyDummyGitPatch() {
    Project project = tmpProject();

    dummyApplicationService.applyDummyGitPatch(project);

    assertJavaFiles(project, "com/mycompany/myapp", "com.mycompany.myapp");
    assertTestFiles(project, "com/mycompany/myapp", "com.mycompany.myapp");
  }

  @Test
  void shouldApplyDummyGitPatchWithSpecificPackageName() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");

    dummyApplicationService.applyDummyGitPatch(project);

    assertJavaFiles(project, "tech/jhipster/chips", "tech.jhipster.chips");
    assertTestFiles(project, "tech/jhipster/chips", "tech.jhipster.chips");
  }
}
