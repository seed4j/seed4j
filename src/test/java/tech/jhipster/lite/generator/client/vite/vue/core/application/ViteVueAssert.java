package tech.jhipster.lite.generator.client.vite.vue.core.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.WordUtils.DQ;
import static tech.jhipster.lite.generator.project.domain.Constants.PACKAGE_JSON;

import java.util.List;
import tech.jhipster.lite.generator.client.vite.vue.core.domain.ViteVue;
import tech.jhipster.lite.generator.project.domain.Project;

public class ViteVueAssert {

  private ViteVueAssert() {}

  public static void assertDependency(Project project) {
    ViteVue.dependencies().forEach(dependency -> assertFileContent(project, PACKAGE_JSON, DQ + dependency + DQ));
    ViteVue.devDependencies().forEach(devDependency -> assertFileContent(project, PACKAGE_JSON, DQ + devDependency + DQ));
  }

  public static void assertScripts(Project project) {
    assertFileContent(project, PACKAGE_JSON, "\"build\": \"vue-tsc --noEmit && vite build --emptyOutDir\"");
    assertFileContent(project, PACKAGE_JSON, "\"dev\": \"vite\"");
    assertFileContent(project, PACKAGE_JSON, "\"preview\": \"vite preview\"");
    assertFileContent(project, PACKAGE_JSON, "\"test\": \"jest src/test/javascript/spec\"");
  }

  public static void assertViteConfigFiles(Project project) {
    assertFileExist(project, ".eslintrc.js");
    assertFileExist(project, "jest.config.js");
    assertFileExist(project, "tsconfig.json");
    assertFileExist(project, "vite.config.ts");
  }

  public static void assertRootFiles(Project project) {
    assertFileExist(project, "src/main/webapp/index.html");
    assertFileExist(project, "src/main/webapp/app/env.d.ts");
    assertFileExist(project, "src/main/webapp/app/main.ts");
  }

  public static void assertAppFiles(Project project) {
    assertFileExist(project, "src/main/webapp/app/common/primary/app/App.component.ts");
    assertFileExist(project, "src/main/webapp/app/common/primary/app/App.html");
    assertFileExist(project, "src/main/webapp/app/common/primary/app/App.vue");
    assertFileExist(project, "src/main/webapp/app/common/primary/app/index.ts");
    assertFileExist(project, "src/test/javascript/spec/common/primary/app/App.spec.ts");
  }

  public static void assertAppWithoutCss(Project project) {
    assertFileNoContent(project, "src/main/webapp/app/common/primary/app/App.vue", "<style>");
  }

  public static void assertAppWithCss(Project project) {
    assertFileContent(project, "src/main/webapp/app/common/primary/app/App.vue", "<style>");
  }

  public static void assertLogos(Project project) {
    assertFileExist(project, "src/main/webapp/content/images/JHipster-Lite-neon-green.png");
    assertFileExist(project, "src/main/webapp/content/images/VueLogo.png");
  }

  public static void assertJestSonar(Project project) {
    assertFileContent(
      project,
      PACKAGE_JSON,
      List.of("\"jestSonar\": {", "\"reportPath\": \"target/test-results/jest\",", "\"reportFile\": \"TESTS-results-sonar.xml\"", "}")
    );
  }
}
