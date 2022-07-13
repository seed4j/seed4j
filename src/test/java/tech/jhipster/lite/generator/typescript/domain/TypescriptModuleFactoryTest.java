package tech.jhipster.lite.generator.typescript.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.packageJsonFile;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class TypescriptModuleFactoryTest {

  private static final TypescriptModuleFactory factory = new TypescriptModuleFactory();

  @Test
  void shouldCreateTypescriptModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildTypescriptModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("package.json")
      .containing(nodeDependency("typescript"))
      .containing(nodeDependency("@typescript-eslint/eslint-plugin"))
      .containing(nodeDependency("@typescript-eslint/parser"))
      .containing(nodeDependency("eslint"))
      .containing(nodeDependency("eslint-import-resolver-typescript"))
      .containing(nodeDependency("eslint-plugin-import"))
      .containing(nodeDependency("eslint-plugin-prettier"))
      .containing(nodeDependency("jest"))
      .containing(nodeDependency("@types/jest"))
      .containing(nodeDependency("ts-jest"))
      .containing("\"test\": \"jest\"")
      .containing("\"test:watch\": \"jest --watch\"")
      .containing("\"test:watch:all\": \"jest --watchAll\"")
      .containing("\"eslint:ci\": \"eslint './**/*.{ts,js}'\"")
      .containing("\"eslint\": \"eslint './**/*.{ts,js}' --fix\"")
      .and()
      .hasPrefixedFiles("", ".eslintrc.js", "jest.config.js", "tsconfig.json");
  }

  private static String nodeDependency(String dependency) {
    return "\"" + dependency + "\": \"";
  }
}
