package tech.jhipster.lite.generator.client.tools.cypress.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.DQ;
import static tech.jhipster.lite.generator.tools.domain.Constants.PACKAGE_JSON;
import static tech.jhipster.lite.generator.tools.domain.Constants.TSCONFIG_JSON;

import tech.jhipster.lite.generator.client.tools.cypress.domain.Cypress;
import tech.jhipster.lite.generator.tools.domain.Project;

public class CypressAssert {

  private CypressAssert() {}

  public static void assertDependency(Project project) {
    Cypress.devDependencies().forEach(devDependency -> assertFileContent(project, PACKAGE_JSON, DQ + devDependency + DQ));
  }

  public static void assertCypressScripts(Project project) {
    assertFileContent(project, PACKAGE_JSON, "e2e");
    assertFileContent(project, PACKAGE_JSON, "e2e:headless");
    assertFileContent(project, PACKAGE_JSON, "test:component");
    assertFileContent(project, PACKAGE_JSON, "test:component:headless");

    assertFileContent(project, TSCONFIG_JSON, "src/test/javascript/integration/**/*spec.ts");
  }

  public static void assertCypressFiles(Project project) {
    assertFileExist(project, "src/test/javascript/integration/cypress-config.json");
    assertFileExist(project, "src/test/javascript/integration/.eslintrc.js");
    assertFileExist(project, "src/test/javascript/integration/tsconfig.json");
  }

  public static void assertCypressTestFiles(Project project) {
    String pathIntegrationTestPrimaryApp = "src/test/javascript/integration/common/primary/app";

    assertFileExist(project, getPath(pathIntegrationTestPrimaryApp, "Home.spec.ts"));
  }
}
