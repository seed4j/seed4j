package tech.jhipster.lite.generator.client.angular.core.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.assertFileNoContent;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.DQ;
import static tech.jhipster.lite.generator.project.domain.Constants.PACKAGE_JSON;

import tech.jhipster.lite.generator.client.angular.core.domain.Angular;
import tech.jhipster.lite.generator.project.domain.Project;

public class AngularAssert {

  private AngularAssert() {}

  public static void assertDevDependencies(Project project) {
    Angular.devDependencies().forEach(devDependency -> assertFileContent(project, PACKAGE_JSON, DQ + devDependency + DQ));
  }

  public static void assertDependencies(Project project) {
    Angular.dependencies().forEach(dependency -> assertFileContent(project, PACKAGE_JSON, DQ + dependency + DQ));
  }

  public static void assertScripts(Project project) {
    assertFileContent(project, PACKAGE_JSON, "ng");
    assertFileContent(project, PACKAGE_JSON, "start");
    assertFileContent(project, PACKAGE_JSON, "build");
    assertFileContent(project, PACKAGE_JSON, "watch");
    assertFileContent(project, PACKAGE_JSON, "test");
  }

  public static void assertConfigFiles(Project project) {
    assertFileExist(project, "angular.json");
    assertFileContent(project, "angular.json", "\"port\": 9000");
    assertFileExist(project, "jest.conf.js");
    assertFileExist(project, "tsconfig.app.json");
    assertFileExist(project, "tsconfig.json");
    assertFileExist(project, "tsconfig.spec.json");
  }

  public static void assertAngularFiles(Project project) {
    String pathWebapp = "src/main/webapp";
    String pathApp = "src/main/webapp/app/common/primary/app";
    String pathEnvironments = "src/main/webapp/environments";

    assertFileExist(project, getPath(pathApp, "app.component.html"));
    assertFileExist(project, getPath(pathApp, "app.component.css"));
    assertFileExist(project, getPath(pathApp, "app.component.spec.ts"));
    assertFileExist(project, getPath(pathApp, "app.component.ts"));
    assertFileExist(project, getPath(pathApp, "app.module.ts"));
    assertFileExist(project, getPath(pathApp, "app-routing.module.ts"));
    assertFileExist(project, getPath(pathApp, "app-routing.module.spec.ts"));
    assertFileExist(project, getPath(pathWebapp, "index.html"));
    assertFileExist(project, getPath(pathWebapp, "main.ts"));
    assertFileExist(project, getPath(pathWebapp, "polyfills.ts"));
    assertFileExist(project, getPath(pathWebapp, "styles.css"));
    assertFileExist(project, getPath(pathEnvironments, "environment.prod.ts"));
    assertFileExist(project, getPath(pathEnvironments, "environment.prod.spec.ts"));
    assertFileExist(project, getPath(pathEnvironments, "environment.ts"));
    assertFileExist(project, getPath(pathEnvironments, "environment.spec.ts"));
  }

  public static void assertAppJwt(Project project) {
    String rootPath = "src/main/webapp/";
    Angular.angularJwtFiles().forEach((file, path) -> assertFileExist(project, getPath(rootPath + path, file)));
  }

  public static void assertLogos(Project project) {
    assertFileExist(project, "src/main/webapp/content/images/JHipster-Lite-neon-red.png");
    assertFileExist(project, "src/main/webapp/content/images/AngularLogo.svg");
  }
}
