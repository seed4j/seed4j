package tech.jhipster.lite.generator.server.javatool.frontendmaven.application;

import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;
import static tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenAssert.assertChecksumMavenPlugin;
import static tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenAssert.assertFrontendMavenPlugin;
import static tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenAssert.assertMavenAntrunPlugin;
import static tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenAssert.assertRedirectionFiles;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class FrontendMavenApplicationServiceIT {

  @Autowired
  FrontendMavenApplicationService frontendMavenApplicationService;

  @Test
  void shouldAddFrontendMavenPlugin() {
    Project project = tmpProjectWithPomXml();

    frontendMavenApplicationService.addFrontendMavenPlugin(project);

    assertChecksumMavenPlugin(project);
    assertMavenAntrunPlugin(project);
    assertFrontendMavenPlugin(project);
    assertRedirectionFiles(project);
  }
}
