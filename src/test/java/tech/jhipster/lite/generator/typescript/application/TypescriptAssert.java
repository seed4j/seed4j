package tech.jhipster.lite.generator.typescript.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.WordUtils.DQ;
import static tech.jhipster.lite.generator.tools.domain.Constants.PACKAGE_JSON;

import tech.jhipster.lite.generator.tools.domain.Project;

public class TypescriptAssert {

  private TypescriptAssert() {}

  public static void assertDevDependencies(Project project) {
    assertFileContent(project, PACKAGE_JSON, "@types/jest" + DQ + ": ");
    assertFileContent(project, PACKAGE_JSON, "@typescript-eslint/eslint-plugin" + DQ + ": ");
    assertFileContent(project, PACKAGE_JSON, "@typescript-eslint/parser" + DQ + ": ");
    assertFileContent(project, PACKAGE_JSON, "eslint" + DQ + ": ");
    assertFileContent(project, PACKAGE_JSON, "eslint-import-resolver-typescript" + DQ + ": ");
    assertFileContent(project, PACKAGE_JSON, "eslint-plugin-import" + DQ + ": ");
    assertFileContent(project, PACKAGE_JSON, "eslint-plugin-prettier" + DQ + ": ");
    assertFileContent(project, PACKAGE_JSON, "jest" + DQ + ": ");
    assertFileContent(project, PACKAGE_JSON, "ts-jest" + DQ + ": ");
    assertFileContent(project, PACKAGE_JSON, "typescript" + DQ + ": ");
  }

  public static void assertScripts(Project project) {
    assertFileContent(project, PACKAGE_JSON, "test");
    assertFileContent(project, PACKAGE_JSON, "test:watch");
    assertFileContent(project, PACKAGE_JSON, "test:watch:all");
    assertFileContent(project, PACKAGE_JSON, "eslint:ci");
    assertFileContent(project, PACKAGE_JSON, "eslint");
  }

  public static void assertConfigFiles(Project project) {
    assertFileExist(project, ".eslintrc.js");
    assertFileExist(project, "jest.config.js");
    assertFileExist(project, "tsconfig.json");
  }
}
