package tech.jhipster.lite.generator.client.vue.core.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.WordUtils.DQ;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_WEBAPP;
import static tech.jhipster.lite.generator.project.domain.Constants.PACKAGE_JSON;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVASCRIPT;

import java.util.List;
import tech.jhipster.lite.generator.client.vue.core.domain.Vue;
import tech.jhipster.lite.generator.project.domain.Project;

public class VueAssert {

  private VueAssert() {}

  public static void assertDependency(Project project) {
    Vue.dependencies().forEach(dependency -> assertFileContent(project, PACKAGE_JSON, DQ + dependency + DQ));
    Vue.routerDependencies().forEach(dependency -> assertFileContent(project, PACKAGE_JSON, DQ + dependency + DQ));
    Vue.devDependencies().forEach(devDependency -> assertFileContent(project, PACKAGE_JSON, DQ + devDependency + DQ));
    Vue.axiosDependency().forEach(dependency -> assertFileContent(project, PACKAGE_JSON, DQ + dependency + DQ));
  }

  public static void assertPiniaDependency(Project project) {
    Vue.piniaDependencies().forEach(dependency -> assertFileContent(project, PACKAGE_JSON, DQ + dependency + DQ));
  }

  public static void assertScripts(Project project) {
    assertFileContent(project, PACKAGE_JSON, "\"build\": \"vue-tsc --noEmit && vite build --emptyOutDir\"");
    assertFileContent(project, PACKAGE_JSON, "\"dev\": \"vite\"");
    assertFileContent(project, PACKAGE_JSON, "\"jest\": \"jest src/test/javascript/spec --logHeapUsage --maxWorkers=2 --no-cache\"");
    assertFileContent(project, PACKAGE_JSON, "\"preview\": \"vite preview\"");
    assertFileContent(project, PACKAGE_JSON, "\"start\": \"vite\"");
    assertFileContent(project, PACKAGE_JSON, "\"test\": \"npm run jest --\"");
    assertFileContent(project, PACKAGE_JSON, "\"test:watch\": \"npm run jest -- --watch\"");
  }

  public static void assertViteConfigFiles(Project project) {
    assertFileExist(project, ".eslintrc.js");
    assertFileExist(project, "jest.config.js");
    assertFileExist(project, "tsconfig.json");
    assertFileExist(project, "vite.config.ts");
    assertFileContent(project, "vite.config.ts", "port: 9000");
  }

  public static void assertRootFiles(Project project) {
    assertFileExist(project, "src/main/webapp/index.html");
    assertFileExist(project, "src/main/webapp/app/env.d.ts");
    assertFileExist(project, "src/main/webapp/app/main.ts");
  }

  public static void assertRouterFiles(Project project) {
    assertFileExist(project, MAIN_WEBAPP, "/app/router/router.ts");
    assertFileExist(project, TEST_JAVASCRIPT, "/router/Router.spec.ts");
  }

  public static void assertAppFiles(Project project) {
    assertFileExist(project, "src/main/webapp/app/common/primary/app/App.component.ts");
    assertFileExist(project, "src/main/webapp/app/common/primary/app/App.html");
    assertFileExist(project, "src/main/webapp/app/common/primary/app/App.vue");
    assertFileExist(project, "src/main/webapp/app/common/primary/app/index.ts");
    assertFileExist(project, "src/test/javascript/spec/common/primary/app/App.spec.ts");
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

  public static void assertAxiosFile(Project project) {
    assertFileExist(project, "src/main/webapp/app/http/AxiosHttp.ts");
    assertFileExist(project, "src/test/javascript/spec/http/AxiosHttp.spec.ts");
    assertFileExist(project, "src/test/javascript/spec/http/AxiosHttpStub.ts");
    assertFileExist(project, "src/test/javascript/spec/http/AxiosStub.ts");
  }

  public static void assertLogger(Project project) {
    assertFileExist(project, "src/main/webapp/app/common/domain/Logger.ts");
    assertFileExist(project, "src/main/webapp/app/common/domain/Message.ts");
    assertFileExist(project, "src/main/webapp/app/common/secondary/ConsoleLogger.ts");
    assertFileExist(project, "src/test/javascript/spec/common/domain/Logger.fixture.ts");
    assertFileExist(project, "src/test/javascript/spec/common/secondary/ConsoleLogger.spec.ts");
  }
}
