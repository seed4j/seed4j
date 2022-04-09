package tech.jhipster.lite.generator.client.svelte.core.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.WordUtils.DQ;
import static tech.jhipster.lite.generator.tools.domain.Constants.PACKAGE_JSON;

import java.util.List;
import tech.jhipster.lite.generator.client.svelte.core.domain.Svelte;
import tech.jhipster.lite.generator.tools.domain.Project;

public class SvelteAssert {

  private SvelteAssert() {}

  public static void assertDependency(Project project) {
    Svelte.dependencies().forEach(dependency -> assertFileContent(project, PACKAGE_JSON, DQ + dependency + DQ));
    Svelte.devDependencies().forEach(devDependency -> assertFileContent(project, PACKAGE_JSON, DQ + devDependency + DQ));
  }

  public static void assertScripts(Project project) {
    assertFileContent(project, PACKAGE_JSON, "\"build\": \"svelte-kit build\"");
    assertFileContent(project, PACKAGE_JSON, "\"dev\": \"svelte-kit dev --port 9000\"");
    assertFileContent(project, PACKAGE_JSON, "\"start\": \"svelte-kit dev --port 9000\"");
    assertFileContent(project, PACKAGE_JSON, "\"preview\": \"svelte-kit preview\"");
    assertFileContent(project, PACKAGE_JSON, "\"package\": \"svelte-kit package\"");
    assertFileContent(project, PACKAGE_JSON, "\"check\": \"svelte-check --tsconfig ./tsconfig.json\"");
    assertFileContent(project, PACKAGE_JSON, "\"check:watch\": \"svelte-check --tsconfig ./tsconfig.json --watch\"");
    assertFileContent(
      project,
      PACKAGE_JSON,
      "\"lint\": \"prettier --ignore-path .gitignore --check --plugin-search-dir=. . && eslint --ignore-path .gitignore .\""
    );
    assertFileContent(project, PACKAGE_JSON, "\"format\": \"prettier --ignore-path .gitignore --write --plugin-search-dir=. .\"");
    assertFileContent(project, PACKAGE_JSON, "\"test\": \"jest\"");
  }

  public static void assertSvelteConfigFiles(Project project) {
    assertFileExist(project, ".eslintrc.cjs");
    assertFileExist(project, "jest.config.cjs");
    assertFileExist(project, "tsconfig.json");
  }

  public static void assertRootFiles(Project project) {
    assertFileExist(project, "src/main/webapp/app.html");
    assertFileExist(project, "src/main/webapp/app.d.ts");
    assertFileExist(project, "src/main/webapp/jest-setup.ts");
  }

  public static void assertAppFiles(Project project) {
    assertFileExist(project, "src/main/webapp/app/common/primary/app/App.svelte");
    assertFileExist(project, "src/main/webapp/routes/index.svelte");
  }

  public static void assertAssets(Project project) {
    assertFileExist(project, "src/main/webapp/assets/JHipster-Lite-neon-orange.png");
    assertFileExist(project, "src/main/webapp/assets/svelte-logo.png");
  }

  public static void assertJestSonar(Project project) {
    assertFileContent(
      project,
      PACKAGE_JSON,
      List.of("\"jestSonar\": {", "\"reportPath\": \"target/test-results/jest\",", "\"reportFile\": \"TESTS-results-sonar.xml\"", "}")
    );
  }

  public static void assertType(Project project) {
    assertFileContent(project, PACKAGE_JSON, List.of("\"type\": \"module\""));
  }
}
