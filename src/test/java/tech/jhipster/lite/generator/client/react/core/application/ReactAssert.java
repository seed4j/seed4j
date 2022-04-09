package tech.jhipster.lite.generator.client.react.core.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.assertFileNoContent;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.DQ;
import static tech.jhipster.lite.generator.tools.domain.Constants.PACKAGE_JSON;

import java.util.List;
import tech.jhipster.lite.generator.client.react.core.domain.React;
import tech.jhipster.lite.generator.tools.domain.Project;

public class ReactAssert {

  private ReactAssert() {}

  public static void assertDependency(Project project) {
    React.dependencies().forEach(dependency -> assertFileContent(project, PACKAGE_JSON, DQ + dependency + DQ));
    React.devDependencies().forEach(devDependency -> assertFileContent(project, PACKAGE_JSON, DQ + devDependency + DQ));
  }

  public static void assertScripts(Project project) {
    assertFileContent(project, PACKAGE_JSON, "dev");
    assertFileContent(project, PACKAGE_JSON, "build");
    assertFileContent(project, PACKAGE_JSON, "preview");
    assertFileContent(project, PACKAGE_JSON, "start");
    assertFileContent(project, PACKAGE_JSON, "test");
    assertFileContent(project, PACKAGE_JSON, "test:watch");
  }

  public static void assertFiles(Project project) {
    assertFileExist(project, "tsconfig.json");
    assertFileExist(project, "vite.config.ts");
    assertFileExist(project, "jest.config.ts");
  }

  public static void assertReactFiles(Project project) {
    String pathWebapp = "src/main/webapp";
    String pathConfig = "src/main/webapp/config";
    String pathApp = "src/main/webapp/app";
    String primaryApp = "src/main/webapp/app/common/primary/app";
    String primaryTestApp = "src/test/javascript/spec/common/primary/app";

    assertFileExist(project, getPath(pathWebapp, "index.html"));
    assertFileExist(project, getPath(pathApp, "index.css"));
    assertFileExist(project, getPath(pathApp, "index.tsx"));
    assertFileExist(project, getPath(pathApp, "vite-env.d.ts"));
    assertFileExist(project, getPath(primaryApp, "App.tsx"));
    assertFileExist(project, getPath(primaryTestApp, "App.spec.tsx"));
    assertFileExist(project, getPath(pathConfig, "setupTests.ts"));
  }

  public static void assertAppWithoutCss(Project project) {
    assertFileNoContent(project, "src/main/webapp/app/common/primary/app/App.tsx", "App.css");
  }

  public static void assertAppWithCss(Project project) {
    assertFileContent(project, "src/main/webapp/app/common/primary/app/App.tsx", "App.css");
  }

  public static void assertJestSonar(Project project) {
    assertFileContent(
      project,
      PACKAGE_JSON,
      List.of("\"jestSonar\": {", "\"reportPath\": \"target/test-results/jest\",", "\"reportFile\": \"TESTS-results-sonar.xml\"", "}")
    );
  }

  public static void assertConfig(Project project) {
    assertFileContent(project, "vite.config.ts", "port: 9000");
  }
}
