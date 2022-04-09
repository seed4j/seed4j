package tech.jhipster.lite.generator.setup.codespaces.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.setup.codespaces.application.CodespacesAssertFiles.assertFilesContainerJson;
import static tech.jhipster.lite.generator.setup.codespaces.application.CodespacesAssertFiles.assertFilesDockerfile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class CodespacesApplicationServiceIT {

  @Autowired
  CodespacesApplicationService codespacesApplicationService;

  @Test
  void shouldInitFiles() {
    Project project = tmpProject();

    codespacesApplicationService.init(project);
    assertFilesDockerfile(project);
    assertFilesContainerJson(project);
  }
}
