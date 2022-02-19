package tech.jhipster.lite.generator.client.vite.vue.core.application;

import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJson;
import static tech.jhipster.lite.generator.client.vite.vue.core.application.ViteVueAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class ViteVueApplicationServiceIT {

  @Autowired
  ViteVueApplicationService viteVueApplicationService;

  @Test
  void shouldAddViteVue() {
    Project project = tmpProjectWithPackageJson();

    viteVueApplicationService.addViteVue(project);

    assertDependency(project);
    assertScripts(project);

    assertViteConfigFiles(project);
    assertRootFiles(project);
    assertAppFiles(project);
    assertAppWithoutCss(project);

    assertJestSonar(project);
  }

  @Test
  void shouldAddStyledViteVue() {
    Project project = tmpProjectWithPackageJson();

    viteVueApplicationService.addStyledViteVue(project);

    assertDependency(project);
    assertScripts(project);

    assertViteConfigFiles(project);
    assertRootFiles(project);
    assertAppFiles(project);
    assertAppWithCss(project);
    assertLogos(project);

    assertJestSonar(project);
  }
}
