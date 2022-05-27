package tech.jhipster.lite.generator.client.vue.core.application;

import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class VueApplicationServiceIT {

  @Autowired
  VueApplicationService vueApplicationService;

  @Test
  void shouldAddVue() {
    Project project = tmpProjectWithPackageJson();

    vueApplicationService.addVue(project);

    VueAssert.assertDependency(project);
    VueAssert.assertScripts(project);

    VueAssert.assertViteConfigFiles(project);
    VueAssert.assertRootFiles(project);
    VueAssert.assertRouterFiles(project);
    VueAssert.assertAppFiles(project);
    VueAssert.assertAppWithCss(project);
    VueAssert.assertLogos(project);
    VueAssert.assertLogger(project);
    VueAssert.assertAxiosFile(project);

    VueAssert.assertJestSonar(project);
  }

  @Test
  void shouldAddPinia() {
    Project project = tmpProjectWithPackageJson();

    vueApplicationService.addVue(project);
    vueApplicationService.addPinia(project);

    VueAssert.assertPiniaDependency(project);
  }
}
